package eu.nk2.impay.request

import com.fasterxml.jackson.annotation.JsonProperty
import eu.nk2.impay.ImpayApiFlavour
import eu.nk2.impay.ImpayApiMethod
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.response.PayLkImpayResponse
import java.math.BigDecimal

class PayLkImpayRequest(
    val amount: BigDecimal,
    @JsonProperty("document_id") val documentId: String,
    @JsonProperty("fullname") val fullName: String,
    @JsonProperty("extid")  val extId: String,
    val timeout: Int,
    @JsonProperty("successurl") val successUrl: String? = null,
    @JsonProperty("failurl") val failUrl: String? = null,
    @JsonProperty("cancelurl") val cancelUrl: String? = null
): AbstractImpayRequest<PayLkImpayResponse>(PayLkImpayResponse::class, ImpayApiMethod("/pay/lk", ImpayApiFlavour.ImpayApiV1Flavour)) {

    override val isValid: Boolean
        get() = timeout in (10..59) && documentId.length <= 40
            && fullName.length <= 80 && extId.length <= 40
}
