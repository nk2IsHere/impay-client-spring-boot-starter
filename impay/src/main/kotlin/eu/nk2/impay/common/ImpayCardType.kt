package eu.nk2.impay.common

import eu.nk2.kjackson.int
import eu.nk2.kjackson.jsonDeserializer
import eu.nk2.kjackson.jsonSerializer
import eu.nk2.kjackson.toJson

sealed class ImpayCardType {

    object InCardType: ImpayCardType()
    object OutCardType: ImpayCardType()
    data class UnknownCardType(val type: Int): ImpayCardType()

    companion object {

        val serializer = jsonSerializer<ImpayCardType> { src, _ -> when(src) {
            is InCardType -> 0.toJson()
            is OutCardType -> 1.toJson()
            is UnknownCardType -> src.type.toJson()
        } }

        val deserializer = jsonDeserializer { src, _ -> when(src.int) {
            0 -> InCardType
            1 -> OutCardType
            else -> UnknownCardType(type = src.int)
        } }
    }
}
