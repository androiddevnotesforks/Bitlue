package com.carlosmesquita.technicaltest.n26.bitlue.repository

import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.BlockchainRemoteDataSource
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.model.mapper.BlockchainResponseDTOMapper
import com.carlosmesquita.technicaltest.n26.bitlue.ui.model.BitcoinRecordInfo
import com.carlosmesquita.technicaltest.n26.bitlue.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class BitcoinRepository(
    private val blockchainRemoteDataSource: BlockchainRemoteDataSource,
    private val blockchainResponseDTOMapper: BlockchainResponseDTOMapper,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun getBitcoinInfo(): Flow<Result<BitcoinRecordInfo>> =
        blockchainRemoteDataSource.getBitcoinInfo()
            .onStart { Result.Loading() }
            .map {
                val mappedResponse = blockchainResponseDTOMapper.mapToDomainModel(it)

                Result.Success(mappedResponse)
            }
            .flowOn(defaultDispatcher)
            .catch { Result.Error(it) }
}
