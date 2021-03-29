package eu.nk2.impay.callback

import com.fasterxml.jackson.annotation.JsonProperty
import eu.nk2.impay.utils.md5
import java.math.BigDecimal

data class ResultCallbackImpayRequest(
    @JsonProperty("extid") val extId: String,
    val id: Int,
    @JsonProperty("sum") val amount: BigDecimal,
    val status: Int,
    val key: String
) {

    val isValid: Boolean
        get() = (extId + id + amount + status).md5() == key
}
