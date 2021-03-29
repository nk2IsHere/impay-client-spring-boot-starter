package eu.nk2.impay.request

import com.fasterxml.jackson.annotation.JsonProperty
import eu.nk2.impay.ImpayApiFlavour
import eu.nk2.impay.ImpayApiMethod
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.response.PayStartImpayResponse
import java.math.BigDecimal

class PayStartImpayRequest(
    val amount: BigDecimal,
    @JsonProperty("document_id") val documentId: String,
    @JsonProperty("fullname") val fullName: String,
    @JsonProperty("extid")  val extId: String,
    val timeout: Int,
    @JsonProperty("cardnumber") val cardNumber: String,
    @JsonProperty("cardholder") val cardHolder: String,
    @JsonProperty("cardyear") val cardYear: String,
    @JsonProperty("cardmonth") val cardMonth: String,
    @JsonProperty("cardcvc") val cardCvc: String,
): AbstractImpayRequest<PayStartImpayResponse>(PayStartImpayResponse::class, ImpayApiMethod("/pay/start", ImpayApiFlavour.ImpayApiV1Flavour)) {

    override val isValid: Boolean
        get() = timeout in (10..59) && documentId.length <= 40
            && fullName.length <= 80 && extId.length <= 40
            && cardNumber.length in (16..18)
}
