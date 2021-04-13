package eu.nk2.impay.request

import eu.nk2.impay.ImpayApiFlavour
import eu.nk2.impay.ImpayApiMethod
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.common.ImpayCardType
import eu.nk2.impay.response.CardInfoImpayResponse

data class CardInfoImpayRequest(
    val card: Int,
    val type: ImpayCardType,
): AbstractImpayRequest<CardInfoImpayResponse>(CardInfoImpayResponse::class, ImpayApiMethod("/card/info", ImpayApiFlavour.ImpayApiV1Flavour)) {

    override val isValid: Boolean
        get() = true
}
