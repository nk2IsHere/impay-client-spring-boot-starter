package eu.nk2.impay.callback

import reactor.core.publisher.Mono

interface ImpayCallbackResponseFactory {

    fun makeResultCallbackResponse(resultCallback: Mono<ResultCallbackImpayRequest>): Mono<ResultCallbackImpayResponse>
    fun makeCheckCallbackResponse(checkCallback: Mono<CheckCallbackImpayRequest>): Mono<CheckCallbackImpayResponse>
}

class DefaultImpayCallbackResponseFactoryImpl: ImpayCallbackResponseFactory {

    override fun makeResultCallbackResponse(resultCallback: Mono<ResultCallbackImpayRequest>): Mono<ResultCallbackImpayResponse> =
        resultCallback.filter { it.isValid }
            .map { ResultCallbackImpayResponse() }
            .defaultIfEmpty(ResultCallbackImpayResponse(
                status = -1,
                message = "Something has gone wrong"
            ))

    override fun makeCheckCallbackResponse(checkCallback: Mono<CheckCallbackImpayRequest>): Mono<CheckCallbackImpayResponse> =
        checkCallback.map { CheckCallbackImpayResponse(success = false) }

}
