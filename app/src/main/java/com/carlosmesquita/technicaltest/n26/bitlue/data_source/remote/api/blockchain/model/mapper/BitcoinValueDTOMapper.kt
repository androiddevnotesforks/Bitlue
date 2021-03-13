package com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.model.mapper

import com.carlosmesquita.technicaltest.n26.bitlue.data_source.remote.api.blockchain.model.BitcoinValueDTO
import com.carlosmesquita.technicaltest.n26.bitlue.ui.model.BitcoinValue
import com.carlosmesquita.technicaltest.n26.bitlue.utils.DomainMapper

class BitcoinValueDTOMapper : DomainMapper<BitcoinValue, BitcoinValueDTO> {

    override fun mapToDomainModel(dtoModel: BitcoinValueDTO): BitcoinValue {
        return BitcoinValue(
            dateMillis = dtoModel.x,
            price = dtoModel.y
        )
    }

    override fun mapToDomainModelList(dtoModelList: List<BitcoinValueDTO>): List<BitcoinValue> {
        return dtoModelList.map { mapToDomainModel(it) }
    }
}
