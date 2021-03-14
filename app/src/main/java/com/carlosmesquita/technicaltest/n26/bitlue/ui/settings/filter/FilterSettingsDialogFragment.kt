package com.carlosmesquita.technicaltest.n26.bitlue.ui.settings.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterRollingAverage
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterTimeRange
import com.carlosmesquita.technicaltest.n26.bitlue.databinding.FragmentDialogFilterSettingsBinding
import com.carlosmesquita.technicaltest.n26.bitlue.ui.MainViewModel
import com.carlosmesquita.technicaltest.n26.bitlue.ui.actions.MainEvents.FilterSettingsEvents
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FilterSettingsDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private val binding: FragmentDialogFilterSettingsBinding
        get() = _binding!!
    private var _binding: FragmentDialogFilterSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogFilterSettingsBinding.inflate(inflater, container, false)

        setupFilters()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setupFilters() {
        setupFilterTimeRange()
        setupFilterRollingAverage()
    }

    private fun setupFilterTimeRange() {
        with(binding) {
            FilterTimeRange.values().forEach {
                val button = when (it) {
                    FilterTimeRange.ALL -> timeRangeFilterAllTimeButton
                    FilterTimeRange.THIRTY_DAYS -> timeRangeFilter30daysButton
                    FilterTimeRange.ONE_YEAR -> timeRangeFilter1yearButton
                    FilterTimeRange.THREE_YEARS -> timeRangeFilter3yearsButton
                }

                button.apply {
                    text = it.displayText
                    isChecked = it == viewModel.selectedTimeRangeFilter.value
                    setOnClickListener { _ ->
                        sendEvent(FilterSettingsEvents.OnFilterTimeRangeClicked(it))
                    }
                }
            }
        }
    }

    private fun setupFilterRollingAverage() {
        with(binding) {
            FilterRollingAverage.values().forEach {
                val button = when (it) {
                    FilterRollingAverage.RAW_VALUES -> rollingAverageFilterRawValuesButton
                    FilterRollingAverage.SEVEN_DAY_AVERAGE -> rollingAverageFilter7dayAverageButton
                    FilterRollingAverage.THIRTY_DAY_AVERAGE -> rollingAverageFilter30daysAverageButton
                }

                button.apply {
                    text = it.displayText
                    isChecked = it == viewModel.selectedRollingAverageFilter.value
                    setOnClickListener { _ ->
                        sendEvent(FilterSettingsEvents.OnFilterRollingAverageClicked(it))
                    }
                }
            }
        }
    }

    private fun sendEvent(event: FilterSettingsEvents) = lifecycleScope.launch {
        viewModel.eventsChannel.send(event)
    }
}
