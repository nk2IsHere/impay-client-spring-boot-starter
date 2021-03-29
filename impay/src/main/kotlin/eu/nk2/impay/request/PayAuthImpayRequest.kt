package eu.nk2.impay.request

import com.fasterxml.jackson.annotation.JsonProperty
import eu.nk2.impay.ImpayApiFlavour
import eu.nk2.impay.ImpayApiMethod
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.response.PayAutoImpayResponse

class PayAuthImpayRequest(
    val status: Int,
    val id: Int,
    @JsonProperty("threedsservertransid") val threeDsServerTransId: String?,
    @JsonProperty("successurl") val successUrl: String? = null,
    @JsonProperty("cardnumber") val cardNumber: String,
    @JsonProperty("cardyear") val cardYear: String,
    @JsonProperty("cardmonth") val cardMonth: String,
    @JsonProperty("clientip") val clientIp: String,
    @JsonProperty("clientacceptheader") val clientAcceptHeader: String,
    @JsonProperty("clientjavaenabled") val clientJsEnabled: Boolean,
    @JsonProperty("clientlanguage") val clientLanguage: String,
    @JsonProperty("clientcolordepth") val clientColorDepth: String,
    @JsonProperty("clientscreenheight") val clientScreenHeight: Int,
    @JsonProperty("clientscreenwidth") val clientScreenWidth: Int,
    @JsonProperty("clienttz") val clientTimeZone: Int,
    @JsonProperty("clientuseragent") val clientUserAgent: String,
    @JsonProperty("challengewindowsize") val challengeWindowSize: Int
): AbstractImpayRequest<PayAutoImpayResponse>(PayAutoImpayResponse::class, ImpayApiMethod("/pay/auth", ImpayApiFlavour.ImpayApiV1Flavour)) {

    override val isValid: Boolean
        get() = challengeWindowSize in (1..5) && status in (0..1)
            && cardNumber.length in (16..18)
}
