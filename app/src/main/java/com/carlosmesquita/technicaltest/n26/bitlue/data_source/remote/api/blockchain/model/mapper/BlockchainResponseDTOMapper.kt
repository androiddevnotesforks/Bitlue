package com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.model.mapper

import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.model.BlockchainResponseDTO
import com.carlosmesquita.technicaltest.n26.bitlue.ui.model.BitcoinRecordInfo
import com.carlosmesquita.technicaltest.n26.bitlue.utils.DomainMapper
import java.util.*

class BlockchainResponseDTOMapper : DomainMapper<BitcoinRecordInfo, BlockchainResponseDTO> {

    override fun mapToDomainModel(dtoModel: BlockchainResponseDTO): BitcoinRecordInfo {
        return BitcoinRecordInfo(
            name = dtoModel.name,
            description = dtoModel.description,
            bitcoinValues = BitcoinValueDTOMapper().mapToDomainModelList(dtoModel.values).onEach {
                it.currency = Currency.getInstance(dtoModel.unit)
            }
        )
    }

    override fun mapToDomainModelList(dtoModelList: List<BlockchainResponseDTO>): List<BitcoinRecordInfo> {
        return dtoModelList.map { mapToDomainModel(it) }
    }
}
