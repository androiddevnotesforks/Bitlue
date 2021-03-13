package com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.model

data class BlockchainResponseDTO(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<BitcoinValueDTO>,
)
