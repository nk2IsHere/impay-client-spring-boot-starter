package eu.nk2.impay.request

import eu.nk2.impay.ImpayApiFlavour
import eu.nk2.impay.ImpayApiMethod
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.response.CardDelImpayResponse

data class CardDelImpayRequest(
    val card: Int,
): AbstractImpayRequest<CardDelImpayResponse>(CardDelImpayResponse::class, ImpayApiMethod("/card/del", ImpayApiFlavour.ImpayApiV1Flavour)) {

    override val isValid: Boolean
        get() = true
}
