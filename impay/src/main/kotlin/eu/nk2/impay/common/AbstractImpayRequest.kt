package eu.nk2.impay.common

import com.fasterxml.jackson.annotation.JsonIgnore
import eu.nk2.impay.ImpayApiMethod
import kotlin.reflect.KClass

abstract class AbstractImpayRequest<T: AbstractImpayResponse>(
    @JsonIgnore val responseClass: KClass<T>,
    @JsonIgnore val method: ImpayApiMethod,
) {
    @get:JsonIgnore abstract val isValid: Boolean
}
