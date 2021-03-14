package com.carlosmesquita.technicaltest.n26.bitlue.ui.actions

sealed class MainStates {

    sealed class BitcoinValueStates : MainStates() {
        object OpenFilterSettings : BitcoinValueStates()
        object ShowLoading : BitcoinValueStates()
        object HideLoading : BitcoinValueStates()
        object ShowFAB : BitcoinValueStates()
        object HideFAB : BitcoinValueStates()
    }

    sealed class FilterSettingsStates : MainStates()
}
