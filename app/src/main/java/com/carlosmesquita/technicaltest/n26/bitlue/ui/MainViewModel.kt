package com.carlosmesquita.technicaltest.n26.bitlue.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosmesquita.technicaltest.n26.bitlue.repository.BitcoinRepository
import com.carlosmesquita.technicaltest.n26.bitlue.ui.model.BitcoinValue
import com.carlosmesquita.technicaltest.n26.bitlue.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: BitcoinRepository
) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _infoTitle = MutableLiveData<CharSequence>()
    val infoTitle: LiveData<CharSequence> = _infoTitle

    private val _currentBitcoinValue = MutableLiveData<String>()
    val currentBitcoinValue: LiveData<String> = _currentBitcoinValue

    private val _bitcoinValues = MutableLiveData<List<BitcoinValue>>()
    val bitcoinValues: LiveData<List<BitcoinValue>> = _bitcoinValues

    init {
        viewModelScope.launch {
            repository.getBitcoinInfo()
                .onStart { emit(Result.Loading()) }
                .catch { emit(Result.Error(it)) }
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _dataLoading.value = true
                        }
                        is Result.Success -> {
                            val bitcoinValues = result.data.bitcoinValues

                            _infoTitle.value = result.data.name
                            _bitcoinValues.value = bitcoinValues
                            _currentBitcoinValue.value = bitcoinValues.last().getPriceStringFormat()

                            _dataLoading.value = false
                        }
                        is Result.Error -> {
                            // TODO
                        }
                    }
                }
        }
    }
}
