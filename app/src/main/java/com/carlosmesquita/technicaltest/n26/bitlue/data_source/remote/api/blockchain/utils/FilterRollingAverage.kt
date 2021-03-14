package com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils

enum class FilterRollingAverage(
    val displayText: String,
    val queryText: String,
    val isDefault: Boolean
) {
    RAW_VALUES("8 Hours", "8hours", true),
    SEVEN_DAY_AVERAGE("7 Days", "7days", false),
    THIRTY_DAY_AVERAGE("30 Days", "30days", false)
}
