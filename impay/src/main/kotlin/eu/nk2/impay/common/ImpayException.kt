package eu.nk2.impay.common

import org.springframework.http.HttpStatus

sealed class ImpayException: Exception {

    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable): super(message, cause)

    object ImpayRequestNotValidException: ImpayException("Request .isValid has returned false - check your request")
    class ImpayResponseHttpException(val error: HttpStatus, val response: AbstractImpayResponse): ImpayException("Something has gone wrong: [${error.value()}]: $response")
    class ImpayResponseGeneralException(cause: Throwable): ImpayException("Something has gone wrong", cause)
}
