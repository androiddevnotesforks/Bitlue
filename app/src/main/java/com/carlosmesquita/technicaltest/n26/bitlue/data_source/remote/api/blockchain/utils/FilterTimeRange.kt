package com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils

enum class FilterTimeRange(
    val displayText: String,
    val queryText: String,
    val isDefault: Boolean
) {
    THIRTY_DAYS("30 Days", "30days", false),
    ONE_YEAR("1 Year", "1year", true),
    THREE_YEARS("3 Years", "3years", false),
    ALL("All", "100years", false)
}
