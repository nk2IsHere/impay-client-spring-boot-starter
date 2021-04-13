package eu.nk2.impay

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import eu.nk2.impay.common.AbstractImpayRequest
import eu.nk2.impay.common.AbstractImpayResponse
import eu.nk2.impay.common.ImpayException
import eu.nk2.impay.response.ErrorImpayResponse
import eu.nk2.impay.utils.sha1
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import kotlin.reflect.KClass

private const val IMPAY_HEADER_LOGIN = "X-Login"
private const val IMPAY_HEADER_TOKEN = "X-Token"

interface ImpayRequestFactory {
    fun <T: AbstractImpayResponse> makeRequest(client: WebClient, api: ImpayApi, request: AbstractImpayRequest<T>): Mono<T>
}

class DefaultImpayRequestFactoryImpl(
    private val objectMapper: ObjectMapper
): ImpayRequestFactory {

    private val log = LoggerFactory.getLogger(ImpayRequestFactory::class.java)

    private data class ImpayRequestContainer(
        val headers: Map<String, String>,
        val body: String,
    )

    private fun <T: AbstractImpayResponse> prepareRequest(api: ImpayApi, request: AbstractImpayRequest<T>) =
        objectMapper.writer().writeValueAsString(request)
            .let {
                ImpayRequestContainer(
                    headers = mapOf(
                        IMPAY_HEADER_LOGIN to api.credentials.id,
                        IMPAY_HEADER_TOKEN to (api.credentials.token.sha1 + it.sha1).sha1
                    ),
                    body = it
                )
            }

    private fun <T: AbstractImpayResponse> decodeResponse(api: ImpayApi, request: AbstractImpayRequest<T>, response: String) =
        objectMapper.readValue(response, request.responseClass.javaObjectType)

    private fun <T: AbstractImpayResponse> decodeResponse(api: ImpayApi, responseClass: KClass<T>, response: String) =
        objectMapper.readValue(response, responseClass.javaObjectType)

    override fun <T: AbstractImpayResponse> makeRequest(client: WebClient, api: ImpayApi, request: AbstractImpayRequest<T>) =
        when(request.isValid) {
            false -> Mono.error(ImpayException.ImpayRequestNotValidException)
            true -> client.post()
                .uri {
                    it.scheme(api.host.scheme)
                        .host(api.host.host)
                        .port(api.host.port)
                        .pathSegment(*(request.method.flavour.path.toTypedArray()), *(request.method.path.toTypedArray()))
                        .build()
                        .apply { log.debug("impay url: $this") }
                }
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .let {
                    val (headers, body) = prepareRequest(api, request)
                        .apply { log.debug("impay request: $this") }

                    it.headers { headers.forEach { (key, value) -> it.add(key, value) } }
                        .body(BodyInserters.fromPublisher(Mono.just(body), String::class.java))
                }
                .retrieve()
                .onStatus({ it.is4xxClientError || it.is5xxServerError }, { response ->
                    response.body(BodyExtractors.toMono(String::class.java))
                        .map { this.decodeResponse(api, ErrorImpayResponse::class, it) }
                        .map { ImpayException.ImpayResponseHttpException(response.statusCode(), it) }
                })
                .bodyToMono(String::class.java)
                .map { this.decodeResponse(api, request, it) }
                .onErrorMap {
                    if(it !is ImpayException) ImpayException.ImpayResponseGeneralException(it)
                    else it
                }
                .retryWhen(Retry.fixedDelay(api.attempts, Duration.ofMillis(api.timeoutMillis)))
        }
}
