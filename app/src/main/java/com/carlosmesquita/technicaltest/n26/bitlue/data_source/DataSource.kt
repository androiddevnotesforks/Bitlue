package com.carlosmesquita.technicaltest.n26.bitlue.data_source

import kotlinx.coroutines.flow.Flow

interface DataSource<DTO> {
    suspend fun getBitcoinInfo(): Flow<DTO>
}