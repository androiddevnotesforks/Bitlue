package com.carlosmesquita.technicaltest.n26.bitlue.repository

import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.BlockchainRemoteDataSource
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.model.mapper.BlockchainResponseDTOMapper
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterRollingAverage
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.utils.FilterTimeRange
import com.carlosmesquita.technicaltest.n26.bitlue.ui.model.BitcoinRecordInfo
import com.carlosmesquita.technicaltest.n26.bitlue.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class BitcoinRepository(
    private val blockchainRemoteDataSource: BlockchainRemoteDataSource,
    private val blockchainResponseDTOMapper: BlockchainResponseDTOMapper,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    fun getBitcoinInfo(
        timeSpan: FilterTimeRange,
        rollingAverage: FilterRollingAverage
    ): Flow<Result<BitcoinRecordInfo>> =
        blockchainRemoteDataSource.getBitcoinInfo(timeSpan, rollingAverage)
            .map {
                val mappedResponse = blockchainResponseDTOMapper.mapToDomainModel(it)

                Result.Success(mappedResponse)
            }
            .flowOn(defaultDispatcher)
}
