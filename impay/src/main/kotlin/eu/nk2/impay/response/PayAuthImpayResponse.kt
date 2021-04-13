package eu.nk2.impay.response

import com.fasterxml.jackson.annotation.JsonProperty
import eu.nk2.impay.common.AbstractImpayResponse
import eu.nk2.impay.common.ImpayError

data class PayAuthImpayResponse(
    val id: Int,
    val url: String,
    val creq: String,
    @JsonProperty("threedssessiondata") val threeDsSessionData: String,
    @JsonProperty("auth3ds") val authThreeDs: String,
    @JsonProperty("dstransid") val dsTransId: String,
    val eci: String,
    override val status: Int,
    override val message: String?,
    override val name: String?,
    override val code: ImpayError?
): AbstractImpayResponse()
