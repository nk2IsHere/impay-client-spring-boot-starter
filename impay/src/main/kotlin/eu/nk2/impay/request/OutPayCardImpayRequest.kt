package eu.nk2.impay.request

import com.fasterxml.jackson.annotation.JsonProperty
import eu.nk2.impay.ImpayApiFlavour
import eu.nk2.impay.ImpayApiMethod
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.response.PayAutoImpayResponse
import java.math.BigDecimal

data class OutPayCardImpayRequest(
    val card: Int? = null,
    @JsonProperty("cardnum") val cardNumber: String? = null,
    val amount: BigDecimal,
    @JsonProperty("extid") val extId: String,
    @JsonProperty("document_id") val documentId: String,
    @JsonProperty("fullname") val fullName: String,
    @JsonProperty("benificphone") val benificPhone: String,
): AbstractImpayRequest<PayAutoImpayResponse>(PayAutoImpayResponse::class, ImpayApiMethod("/out/paycard", ImpayApiFlavour.ImpayApiV1Flavour)) {

    override val isValid: Boolean
        get() = (card != null || cardNumber != null)
            && cardNumber?.length?.let { it in (16..18) } ?: true
            && extId.length <= 40 && documentId.length <= 40 && fullName.length <= 80
}
