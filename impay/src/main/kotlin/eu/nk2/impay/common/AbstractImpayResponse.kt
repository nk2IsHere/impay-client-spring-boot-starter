package eu.nk2.impay.common

abstract class AbstractImpayResponse(
    val status: Int,
    val message: String?,
    val name: String?,
    val code: ImpayError?
)
