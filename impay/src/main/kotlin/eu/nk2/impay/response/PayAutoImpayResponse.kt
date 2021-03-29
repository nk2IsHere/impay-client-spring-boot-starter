package eu.nk2.impay.response

import eu.nk2.impay.common.AbstractImpayResponse
import eu.nk2.impay.common.ImpayError

class PayAutoImpayResponse(
    val id: Int,
    status: Int,
    message: String?,
    name: String?,
    code: ImpayError?
): AbstractImpayResponse(
    status = status,
    message = message,
    name = name,
    code = code
)
