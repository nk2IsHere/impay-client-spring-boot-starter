package eu.nk2.impay.common

data class ImpayCard(
    val id: Int,
    val num: String,
    val exp: String?,
    val holder: String
)
