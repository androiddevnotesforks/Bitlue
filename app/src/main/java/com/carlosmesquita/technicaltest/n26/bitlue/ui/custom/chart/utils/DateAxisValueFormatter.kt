package com.carlosmesquita.technicaltest.n26.bitlue.ui.custom.chart.utils

import com.carlosmesquita.technicaltest.n26.bitlue.utils.extensions.toDateFormat
import com.carlosmesquita.technicaltest.n26.bitlue.utils.extensions.unixToMillis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class DateAxisValueFormatter : IndexAxisValueFormatter() {

    companion object {
        const val DEFAULT_DATE_FORMAT = "MMM yy"
    }

    override fun getFormattedValue(value: Float): String {
        return value.toLong().unixToMillis().toDateFormat(DEFAULT_DATE_FORMAT)
    }
}
