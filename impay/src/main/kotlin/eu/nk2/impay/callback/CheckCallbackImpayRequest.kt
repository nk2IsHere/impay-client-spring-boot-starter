package eu.nk2.impay.callback

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class CheckCallbackImpayRequest(
    @JsonProperty("extid") val extId: String,
    @JsonProperty("sum") val amount: BigDecimal
)
