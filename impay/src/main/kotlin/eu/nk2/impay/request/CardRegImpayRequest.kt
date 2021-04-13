package eu.nk2.impay.request

import com.fasterxml.jackson.annotation.JsonProperty
import eu.nk2.impay.ImpayApiFlavour
import eu.nk2.impay.ImpayApiMethod
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.common.ImpayCardType
import eu.nk2.impay.response.CardRegImpayResponse

data class CardRegImpayRequest(
    val type: ImpayCardType,
    @JsonProperty("extid") val extId: String,
    val timeout: Int,
    @JsonProperty("successurl") val successUrl: String? = null,
    @JsonProperty("failurl") val failUrl: String? = null,
    @JsonProperty("cancelurl") val cancelUrl: String? = null
): AbstractImpayRequest<CardRegImpayResponse>(CardRegImpayResponse::class, ImpayApiMethod("/card/reg", ImpayApiFlavour.ImpayApiV1Flavour)) {

    override val isValid: Boolean
        get() = timeout in (10..59) && extId.length <= 40
}
