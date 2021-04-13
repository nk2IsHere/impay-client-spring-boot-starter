package eu.nk2.impay.common

import com.fasterxml.jackson.annotation.JsonIgnore
import eu.nk2.impay.ImpayApiMethod
import kotlin.reflect.KClass

abstract class AbstractImpayRequest<T: AbstractImpayResponse>(
    @JsonIgnore val responseClass: KClass<T>,
    @JsonIgnore val method: ImpayApiMethod,
) {
    @get:JsonIgnore abstract val isValid: Boolean

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractImpayRequest<*>

        if (responseClass != other.responseClass) return false
        if (method != other.method) return false

        return true
    }

    override fun hashCode(): Int {
        var result = responseClass.hashCode()
        result = 31 * result + method.hashCode()
        return result
    }

    abstract override fun toString(): String
}
