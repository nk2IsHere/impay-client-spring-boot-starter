package eu.nk2.impay.common

import eu.nk2.kjackson.int
import eu.nk2.kjackson.jsonDeserializer
import eu.nk2.kjackson.jsonSerializer
import eu.nk2.kjackson.toJson

sealed class ImpayError {

    data class UnknownImpayError(val code: Int): ImpayError()

    companion object {

        val serializer = jsonSerializer<ImpayError> { src, _ -> when(src) {
            is UnknownImpayError -> src.code.toJson()
        } }

        val deserializer = jsonDeserializer { src, _ -> when(src.int) {
            else -> UnknownImpayError(src.int)
        } }
    }
}
