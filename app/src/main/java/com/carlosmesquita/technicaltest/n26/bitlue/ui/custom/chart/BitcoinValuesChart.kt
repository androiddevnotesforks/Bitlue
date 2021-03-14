package com.carlosmesquita.technicaltest.n26.bitlue.ui.custom.chart

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.carlosmesquita.technicaltest.n26.bitlue.R
import com.carlosmesquita.technicaltest.n26.bitlue.ui.custom.chart.utils.DateAxisValueFormatter
import com.carlosmesquita.technicaltest.n26.bitlue.ui.custom.chart.utils.PriceAxisValueFormatter
import com.carlosmesquita.technicaltest.n26.bitlue.ui.custom.chart.utils.PriceMarkerView
import com.carlosmesquita.technicaltest.n26.bitlue.ui.model.BitcoinValue
import com.carlosmesquita.technicaltest.n26.bitlue.utils.extensions.getThemeColor
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.*

class BitcoinValuesChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LineChart(context, attrs, defStyle) {

    companion object {
        const val CHART_X_ANIMATION_DURATION = 2000
        val DEFAULT_ANIMATION_EASING: Easing.EasingFunction = Easing.EaseInOutQuad
    }

    private val xAxisValueFormatter = DateAxisValueFormatter()
    private val yAxisValueFormatter = PriceAxisValueFormatter()

    init {
        // No description or legend needed
        description.isEnabled = false
        legend.isEnabled = false

        // Disable any kind of zoom behaviour
        setScaleEnabled(false)
        isDoubleTapToZoomEnabled = false

        // Let user touch to highlight a selected value
        setTouchEnabled(true)

        setBorderColor(context.getThemeColor(R.attr.colorPrimary))
        marker = PriceMarkerView(context, R.layout.view_chart_marker).apply {
            chartView = this@BitcoinValuesChart
        }

        setupNoDataText()
        setupXAxis()
        setupYAxis()
    }

    fun setBitcoinValues(bitcoinValues: List<BitcoinValue>) {
        CoroutineScope(Dispatchers.Default).launch {
            data = getData(bitcoinValues)

            withContext(Dispatchers.Main) {
                animateX(CHART_X_ANIMATION_DURATION, DEFAULT_ANIMATION_EASING)
            }
        }
    }

    private fun setupXAxis() {
        xAxis.valueFormatter = xAxisValueFormatter
        xAxis.gridColor = ContextCompat.getColor(context, R.color.gray_light)
        xAxis.setDrawAxisLine(false)
    }

    private fun setupYAxis() {
        axisLeft.valueFormatter = yAxisValueFormatter
        axisLeft.setDrawAxisLine(false)
        axisLeft.setDrawZeroLine(false)
        axisLeft.setDrawGridLines(false)

        axisRight.isEnabled = false
    }

    private fun setupNoDataText(
        text: String = context.getString(R.string.loading),
        @ColorInt textColor: Int = context.getThemeColor(R.attr.colorSecondary)
    ) {
        setNoDataText(text)
        setNoDataTextColor(textColor)
    }

    private fun getData(bitcoinValues: List<BitcoinValue>): LineData {
        val entries = bitcoinValues.map {
            Entry(it.dateMillis.toFloat(), it.price.toFloat(), it.getPriceStringFormat())
        }
        val lineDataSet = getLineDataSet(entries)

        return LineData(lineDataSet)
    }

    private fun getLineDataSet(entries: List<Entry>) = LineDataSet(entries, "").apply {
        color = context.getThemeColor(R.attr.colorPrimary)
        fillDrawable =
            ContextCompat.getDrawable(context, R.drawable.gradient_primary_to_transparent)
        valueTextSize = 0f

        setDrawFilled(true)
        setDrawCircles(false)

        highLightColor = context.getThemeColor(R.attr.colorSecondary)
        setDrawHorizontalHighlightIndicator(false)
    }
}
