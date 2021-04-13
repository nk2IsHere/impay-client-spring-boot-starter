package eu.nk2.impay.response

import com.fasterxml.jackson.annotation.JsonProperty
import eu.nk2.impay.common.AbstractImpayResponse
import eu.nk2.impay.common.ImpayError

data class PayStartImpayResponse(
    val id: Int,
    val url: String,
    @JsonProperty("3dsvers") val threeDsVersion: Int,
    @JsonProperty("threedsservertransid") val threeDsServerTransId: String?,
    @JsonProperty("idcard") val threeDsCardId: String?,
    @JsonProperty("pa") val threeDsPa: String?,
    @JsonProperty("md") val threeDsMd: String?,
    override val status: Int,
    override val message: String?,
    override val name: String?,
    override val code: ImpayError?
): AbstractImpayResponse()
