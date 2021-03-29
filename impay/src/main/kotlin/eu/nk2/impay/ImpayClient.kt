package eu.nk2.impay

import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.common.AbstractImpayResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

interface ImpayClient {
    fun <T: AbstractImpayResponse> makeRequest(request: AbstractImpayRequest<T>): Mono<T>
}

class DefaultImpayClientImpl(
    private val api: ImpayApi,
    private val requestFactory: ImpayRequestFactory,
    private val client: WebClient
): ImpayClient {

    override fun <T : AbstractImpayResponse> makeRequest(request: AbstractImpayRequest<T>) =
        requestFactory.makeRequest(client, api, request)
}
