package eu.nk2.impay.callback

const val IMPAY_RESULT_CALLBACK_SUCCESS = 0

data class ResultCallbackImpayResponse(
    val status: Int = IMPAY_RESULT_CALLBACK_SUCCESS,
    val message: String? = null
)
