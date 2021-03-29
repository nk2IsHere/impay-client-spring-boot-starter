package eu.nk2.impay.request

import com.fasterxml.jackson.annotation.JsonProperty
import eu.nk2.impay.ImpayApiFlavour
import eu.nk2.impay.ImpayApiMethod
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.response.PayAutoImpayResponse
import java.math.BigDecimal

class PayAutoImpayRequest(
    val card: Int,
    val amount: BigDecimal,
    @JsonProperty("extid") val extId: String
): AbstractImpayRequest<PayAutoImpayResponse>(PayAutoImpayResponse::class, ImpayApiMethod("/pay/auto", ImpayApiFlavour.ImpayApiV1Flavour)) {

    override val isValid: Boolean
        get() = extId.length <= 40
}
