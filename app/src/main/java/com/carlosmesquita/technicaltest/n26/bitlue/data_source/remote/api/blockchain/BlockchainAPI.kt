package com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain

import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.model.BlockchainResponseDTO
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterRollingAverage
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterTimeRange
import retrofit2.http.GET
import retrofit2.http.Query

interface BlockchainAPI {

    companion object {
        const val BASE_URL = "https://api.blockchain.info/"

        const val CHARTS_ENDPOINT = "charts"
        const val MARKET_PRICE_ENDPOINT = "market-price"

        const val TIME_SPAN_QUERY = "timespan"
        const val ROLLING_AVERAGE_QUERY = "rollingAverage"
    }

    @GET("$CHARTS_ENDPOINT/$MARKET_PRICE_ENDPOINT")
    suspend fun getMarketPriceChart(
        @Query(TIME_SPAN_QUERY) timeSpan: String =
            FilterTimeRange.ONE_YEAR.queryText,
        @Query(ROLLING_AVERAGE_QUERY) rollingAverage: String =
            FilterRollingAverage.RAW_VALUES.queryText,
    ): BlockchainResponseDTO
}
