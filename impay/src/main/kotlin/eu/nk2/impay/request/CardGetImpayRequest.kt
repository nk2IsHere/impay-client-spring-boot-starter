package eu.nk2.impay.request

import eu.nk2.impay.ImpayApiFlavour
import eu.nk2.impay.ImpayApiMethod
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.common.ImpayCardType
import eu.nk2.impay.response.CardGetImpayResponse

class CardGetImpayRequest(
    val id: Int,
    val type: ImpayCardType,
): AbstractImpayRequest<CardGetImpayResponse>(CardGetImpayResponse::class, ImpayApiMethod("/card/get", ImpayApiFlavour.ImpayApiV1Flavour)) {

    override val isValid: Boolean
        get() = true
}
