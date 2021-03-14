package com.carlosmesquita.technicaltest.n26.bitlue.ui.custom.chart.utils

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlin.math.roundToInt

class PriceAxisValueFormatter : IndexAxisValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return if (value > 1000f) {
            "${(value / 1000).roundToInt()}K"
        } else if (value <= 0f) {
            ""
        } else {
            value.toString()
        }
    }
}
