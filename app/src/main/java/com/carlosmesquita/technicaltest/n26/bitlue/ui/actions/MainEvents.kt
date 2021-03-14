package com.carlosmesquita.technicaltest.n26.bitlue.ui.actions

import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterRollingAverage
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterTimeRange

sealed class MainEvents {

    sealed class BitcoinValueEvents : MainEvents() {
        object OnFabClicked : BitcoinValueEvents()
        object OnThemeToggleClicked : BitcoinValueEvents()
    }

    sealed class FilterSettingsEvents : MainEvents() {
        data class OnFilterTimeRangeClicked(val filterTimeRange: FilterTimeRange) :
            FilterSettingsEvents()

        data class OnFilterRollingAverageClicked(val filterRollingAverage: FilterRollingAverage) :
            FilterSettingsEvents()
    }
}
