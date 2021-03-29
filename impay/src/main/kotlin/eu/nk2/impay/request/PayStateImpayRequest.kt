package eu.nk2.impay.request

import eu.nk2.impay.ImpayApiFlavour
import eu.nk2.impay.ImpayApiMethod
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.response.PayStateImpayResponse

class PayStateImpayRequest(
    val id: Int,
): AbstractImpayRequest<PayStateImpayResponse>(PayStateImpayResponse::class, ImpayApiMethod("/pay/state", ImpayApiFlavour.ImpayApiV1Flavour)) {

    override val isValid: Boolean
        get() = true
}
