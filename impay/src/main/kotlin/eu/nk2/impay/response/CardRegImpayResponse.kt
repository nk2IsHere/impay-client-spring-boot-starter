package eu.nk2.impay.response

import eu.nk2.impay.common.AbstractImpayResponse
import eu.nk2.impay.common.ImpayError

data class CardRegImpayResponse(
    val id: Int,
    val url: String,
    override val status: Int,
    override val message: String?,
    override val name: String?,
    override val code: ImpayError?
): AbstractImpayResponse()
