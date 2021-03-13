package com.carlosmesquita.technicaltest.n26.bitlue.di

import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.BlockchainRemoteDataSource
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.model.mapper.BlockchainResponseDTOMapper
import com.carlosmesquita.technicaltest.n26.bitlue.repository.BitcoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        blockchainRemoteDataSource: BlockchainRemoteDataSource,
        blockchainResponseDTOMapper: BlockchainResponseDTOMapper
    ) =
        BitcoinRepository(blockchainRemoteDataSource, blockchainResponseDTOMapper)
}
