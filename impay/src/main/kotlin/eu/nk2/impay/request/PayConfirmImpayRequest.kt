package eu.nk2.impay.request

import com.fasterxml.jackson.annotation.JsonProperty
import eu.nk2.impay.ImpayApiFlavour
import eu.nk2.impay.ImpayApiMethod
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.response.PayConfirmImpayResponse

class PayConfirmImpayRequest(
    val id: Int,
    @JsonProperty("pares") val pares: String? = null,
    @JsonProperty("md") val md: String? = null,
    @JsonProperty("cardnumber") val cardNumber: String? = null,
    @JsonProperty("cres") val cres: String? = null,
    @JsonProperty("auth3ds") val authThreeDs: String? = null,
    @JsonProperty("dstransid") val dsTransId: String? = null,
    val eci: String? = null,
    @JsonProperty("idcard") val idCard: String? = null,
): AbstractImpayRequest<PayConfirmImpayResponse>(PayConfirmImpayResponse::class, ImpayApiMethod("/pay/confirm", ImpayApiFlavour.ImpayApiV1Flavour)) {

    override val isValid: Boolean
        get() = (pares != null && md != null)
            || (cardNumber != null && cres != null && authThreeDs != null && dsTransId != null && eci != null && idCard != null)
}
