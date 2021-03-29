package eu.nk2.impay.request

import eu.nk2.impay.ImpayApiFlavour
import eu.nk2.impay.ImpayApiMethod
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.response.OutStateImpayResponse

class OutStateImpayRequest(
    val id: Int,
): AbstractImpayRequest<OutStateImpayResponse>(OutStateImpayResponse::class, ImpayApiMethod("/out/state", ImpayApiFlavour.ImpayApiV1Flavour)) {

    override val isValid: Boolean
        get() = true
}
