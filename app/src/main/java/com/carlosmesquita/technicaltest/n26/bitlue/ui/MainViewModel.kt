package com.carlosmesquita.technicaltest.n26.bitlue.ui

import androidx.lifecycle.*
import com.carlosmesquita.technicaltest.n26.bitlue.BuildConfig
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterRollingAverage
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterTimeRange
import com.carlosmesquita.technicaltest.n26.bitlue.repository.BitcoinRepository
import com.carlosmesquita.technicaltest.n26.bitlue.ui.actions.MainEvents
import com.carlosmesquita.technicaltest.n26.bitlue.ui.actions.MainEvents.BitcoinValueEvents
import com.carlosmesquita.technicaltest.n26.bitlue.ui.actions.MainEvents.FilterSettingsEvents
import com.carlosmesquita.technicaltest.n26.bitlue.ui.actions.MainStates
import com.carlosmesquita.technicaltest.n26.bitlue.ui.actions.MainStates.BitcoinValueStates
import com.carlosmesquita.technicaltest.n26.bitlue.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    repository: BitcoinRepository
) : ViewModel() {

    val eventsChannel = Channel<MainEvents>(Channel.BUFFERED)
    private val events = eventsChannel.receiveAsFlow()

    private val _states = MutableSharedFlow<MainStates>()
    val states = _states.asSharedFlow()

    private val _selectedTimeRangeFilter =
        MutableLiveData(FilterTimeRange.values().first { it.isDefault })
    val selectedTimeRangeFilter: LiveData<FilterTimeRange> = _selectedTimeRangeFilter

    private val _selectedRollingAverageFilter =
        MutableLiveData(FilterRollingAverage.values().first { it.isDefault })
    val selectedRollingAverageFilter: LiveData<FilterRollingAverage> = _selectedRollingAverageFilter

    private val bitcoinInfoFlow = combine(
        selectedTimeRangeFilter.asFlow(),
        selectedRollingAverageFilter.asFlow()
    ) { timeRangeFilter, rollingAverageFilter ->
        Pair(timeRangeFilter, rollingAverageFilter)
    }.flatMapLatest { (timeRangeFilter, rollingAverageFilter) ->
        repository.getBitcoinInfo(timeRangeFilter, rollingAverageFilter)
    }.onStart {
        resumeLoading()
    }.catch {
        emit(Result.Error(it))
    }.onCompletion {
        stopLoading()
    }

    val errorMessage = bitcoinInfoFlow
        .filter { it is Result.Error }
        .map { (it as Result.Error).throwable.message }
        .asLiveData()

    val currentBitcoinValue = liveData {
        val firstSuccessResult = bitcoinInfoFlow
            .firstOrNull { it is Result.Success } as? Result.Success ?: return@liveData

        emit(firstSuccessResult.data.bitcoinValues.last().getPriceStringFormat())
    }

    val infoTitle = bitcoinInfoFlow
        .filter { it is Result.Success }
        .map { (it as Result.Success).data.name }
        .asLiveData()

    val bitcoinValues = bitcoinInfoFlow
        .filter { it is Result.Success }
        .map { (it as Result.Success).data.bitcoinValues }
        .asLiveData()

    init {
        viewModelScope.launch {
            events.collect {
                Timber.i("Event triggered => ${it::class.java.name}")

                when (it) {
                    is BitcoinValueEvents -> handleBitcoinValueEvent(it)
                    is FilterSettingsEvents -> handleFilterSettingsEvent(it)
                }
            }
        }
    }

    private fun handleBitcoinValueEvent(event: BitcoinValueEvents) = viewModelScope.launch {
        when (event) {
            BitcoinValueEvents.OnFabClicked -> {
                sendStateToUI(BitcoinValueStates.OpenFilterSettings)
            }

            else -> {
                if (BuildConfig.DEBUG) {
                    throw IllegalStateException(
                        "Unknown BitcoinValueEvents instance: ${event::class.java.simpleName}"
                    )
                }
            }
        }
    }

    private fun handleFilterSettingsEvent(event: FilterSettingsEvents) = viewModelScope.launch {
        when (event) {
            is FilterSettingsEvents.OnFilterTimeRangeClicked -> {
                _selectedTimeRangeFilter.value = event.filterTimeRange
            }

            is FilterSettingsEvents.OnFilterRollingAverageClicked -> {
                _selectedRollingAverageFilter.value = event.filterRollingAverage
            }

            else -> {
                if (BuildConfig.DEBUG) {
                    throw IllegalStateException(
                        "Unknown FilterSettingsEvents instance: ${event::class.java.simpleName}"
                    )
                }
            }
        }
    }

    private fun resumeLoading() = viewModelScope.launch {
        sendStateToUI(BitcoinValueStates.HideFAB)
        sendStateToUI(BitcoinValueStates.ShowLoading)
    }

    private fun stopLoading() = viewModelScope.launch {
        sendStateToUI(BitcoinValueStates.HideLoading)
        sendStateToUI(BitcoinValueStates.ShowFAB)
    }

    private suspend fun sendStateToUI(states: MainStates) = viewModelScope.launch {
        _states.emit(states)
    }
}
