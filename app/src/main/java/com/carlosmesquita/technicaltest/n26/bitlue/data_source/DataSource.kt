package com.carlosmesquita.technicaltest.n26.bitlue.data_source

import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterRollingAverage
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterTimeRange
import kotlinx.coroutines.flow.Flow

interface DataSource<DTO> {
    fun getBitcoinInfo(timeSpan: FilterTimeRange, rollingAverage: FilterRollingAverage): Flow<DTO>
}