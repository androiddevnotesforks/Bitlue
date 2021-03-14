package com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote

import com.carlosmesquita.technicaltest.n26.bitlue.data_source.DataSource
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.BlockchainAPI
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.model.BlockchainResponseDTO
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterRollingAverage
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterTimeRange
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BlockchainRemoteDataSource(
    private val blockchainAPI: BlockchainAPI,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataSource<BlockchainResponseDTO> {

    override fun getBitcoinInfo(
        timeSpan: FilterTimeRange,
        rollingAverage: FilterRollingAverage
    ): Flow<BlockchainResponseDTO> = flow {
        emit(
            blockchainAPI.getMarketPriceChart(
                timeSpan = timeSpan.queryText,
                rollingAverage = rollingAverage.queryText
            )
        )
    }.flowOn(defaultDispatcher)
}
