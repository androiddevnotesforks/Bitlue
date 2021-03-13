package com.carlosmesquita.technicaltest.n26.bitlue.di

import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.BlockchainRemoteDataSource
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.BlockchainAPI
import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.model.mapper.BlockchainResponseDTOMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideBlockchainAPI(): BlockchainAPI = Retrofit.Builder()
        .baseUrl(BlockchainAPI.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BlockchainAPI::class.java)

    @Singleton
    @Provides
    fun provideBlockchainResponseDTOMapper() = BlockchainResponseDTOMapper()

    @Singleton
    @Provides
    fun provideBlockchainRemoteDataSource(blockchainAPI: BlockchainAPI) =
        BlockchainRemoteDataSource(blockchainAPI)
}
