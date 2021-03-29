package eu.nk2.impay.response

import com.fasterxml.jackson.annotation.JsonProperty
import eu.nk2.impay.common.AbstractImpayResponse
import eu.nk2.impay.common.ImpayError

class PayStartImpayResponse(
    val id: Int,
    val url: String,
    @JsonProperty("3dsvers") val threeDsVersion: Int,
    @JsonProperty("threedsservertransid") val threeDsServerTransId: String?,
    @JsonProperty("idcard") val threeDsCardId: String?,
    @JsonProperty("pa") val threeDsPa: String?,
    @JsonProperty("md") val threeDsMd: String?,
    status: Int,
    message: String?,
    name: String?,
    code: ImpayError?
): AbstractImpayResponse(
    status = status,
    message = message,
    name = name,
    code = code
)
