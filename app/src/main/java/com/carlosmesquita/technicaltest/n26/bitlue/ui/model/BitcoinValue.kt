package com.carlosmesquita.technicaltest.n26.bitlue.ui.model

import java.text.NumberFormat
import java.util.*

data class BitcoinValue(
    val dateMillis: Long,
    val price: Double,
) {

    lateinit var currency: Currency

    companion object {
        const val MAX_FRACTION_DIGITS = 2
    }

    fun getPriceStringFormat(): String {
        val priceNumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())

        priceNumberFormat.maximumFractionDigits = MAX_FRACTION_DIGITS
        priceNumberFormat.currency = this.currency

        return priceNumberFormat.format(price)
    }
}
