package eu.nk2.impay.response

import com.fasterxml.jackson.annotation.JsonProperty
import eu.nk2.impay.common.AbstractImpayResponse
import eu.nk2.impay.common.ImpayError

class PayAuthImpayResponse(
    val id: Int,
    val url: String,
    val creq: String,
    @JsonProperty("threedssessiondata") val threeDsSessionData: String,
    @JsonProperty("auth3ds") val authThreeDs: String,
    @JsonProperty("dstransid") val dsTransId: String,
    val eci: String,
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
