package eu.nk2.impay

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import eu.nk2.impay.callback.*
import eu.nk2.impay.common.ImpayCardType
import eu.nk2.impay.common.ImpayError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.*
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.type.AnnotatedTypeMetadata
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

private const val IMPAY_OBJECT_MAPPER_BEAN_ID = "impayObjectMapper"
private const val IMPAY_WEB_CLIENT_BEAN_ID = "impayWebClient"
private const val IMPAY_WEB_CALLBACK_API_BEAN_ID = "impayWebCallbackApi"

internal class ImpayObjectMapperBeanExistsCondition: Condition {

    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean =
        context.beanFactory!!
            .getBeanNamesForType(ObjectMapper::class.java)
            .any { it == IMPAY_OBJECT_MAPPER_BEAN_ID }
            .not()
}

internal class ImpayWebClientBeanExistsCondition: Condition {

    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean =
        context.beanFactory!!
            .getBeanNamesForType(WebClient::class.java)
            .any { it == IMPAY_WEB_CLIENT_BEAN_ID }
            .not()
}

internal class ImpayWebCallbackApiBeanExistsCondition: Condition {

    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean =
        context.beanFactory!!
            .getBeanNamesForType(RouterFunction::class.java)
            .any { it == IMPAY_WEB_CALLBACK_API_BEAN_ID }
            .not()
}


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(ImpayConfiguration::class)
annotation class ImportImpayConfiguration

@Configuration
@EnableConfigurationProperties(ImpayConfigurationProperties::class)
class ImpayConfiguration {

    @Bean @Qualifier(IMPAY_OBJECT_MAPPER_BEAN_ID)
    @Order(Ordered.LOWEST_PRECEDENCE)
    @Conditional(ImpayObjectMapperBeanExistsCondition::class)
    fun impayObjectMapper(): ObjectMapper =
        jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(
                SimpleModule()
                    .addSerializer(ImpayError::class.java, ImpayError.serializer)
                    .addDeserializer(ImpayError::class.java, ImpayError.deserializer)
                    .addSerializer(ImpayCardType::class.java, ImpayCardType.serializer)
                    .addDeserializer(ImpayCardType::class.java, ImpayCardType.deserializer)
            )

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    @ConditionalOnMissingBean(ImpayApiHost::class)
    fun impayApiHost(
        @Autowired properties: ImpayConfigurationProperties
    ): ImpayApiHost = when(properties.hostMode) {
        ImpayConfigurationProperties.ImpayConfigurationPropertiesHostMode.DEBUG -> ImpayApiHost.ImpayApiDebugHost
        ImpayConfigurationProperties.ImpayConfigurationPropertiesHostMode.PRODUCTION -> ImpayApiHost.ImpayApiProductionHost
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    @ConditionalOnMissingBean(ImpayApiCredentials::class)
    fun impayApiCredentials(
        @Autowired properties: ImpayConfigurationProperties
    ): ImpayApiCredentials = when(properties.credentialsMode) {
        ImpayConfigurationProperties.ImpayConfigurationPropertiesCredentialsMode.DEBUG -> ImpayApiCredentials.ImpayApiDemoCredentials
        ImpayConfigurationProperties.ImpayConfigurationPropertiesCredentialsMode.PRODUCTION -> ImpayApiCredentials.ImpayApiProductionCredentials(
            id = properties.merchantId ?: error("impay.merchantId is required in application.properties"),
            token = properties.merchantToken ?: error("impay.merchantToken is required in application.properties")
        )
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    @ConditionalOnMissingBean(ImpayApi::class)
    fun impayApi(
        @Autowired impayApiHost: ImpayApiHost,
        @Autowired impayApiCredentials: ImpayApiCredentials,
        @Autowired properties: ImpayConfigurationProperties
    ): ImpayApi =
        ImpayApi(
            host = impayApiHost,
            credentials = impayApiCredentials,
            timeoutMillis = properties.timeoutMillis,
            attempts = properties.attempts
        )

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    @ConditionalOnMissingBean(ImpayRequestFactory::class)
    fun impayRequestFactory(
        @Autowired @Qualifier(IMPAY_OBJECT_MAPPER_BEAN_ID) objectMapper: ObjectMapper
    ): ImpayRequestFactory =
        DefaultImpayRequestFactoryImpl(
            objectMapper = objectMapper
        )

    @Bean @Qualifier(IMPAY_WEB_CLIENT_BEAN_ID)
    @Order(Ordered.LOWEST_PRECEDENCE)
    @Conditional(ImpayWebClientBeanExistsCondition::class)
    fun impayWebClient(
        @Autowired properties: ImpayConfigurationProperties
    ): WebClient =
        WebClient.builder()
            .build()

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    @ConditionalOnMissingBean(ImpayClient::class)
    fun impayClient(
        @Autowired impayApi: ImpayApi,
        @Autowired impayRequestFactory: ImpayRequestFactory,
        @Autowired @Qualifier(IMPAY_WEB_CLIENT_BEAN_ID) impayWebClient: WebClient
    ): ImpayClient =
        DefaultImpayClientImpl(
            api = impayApi,
            requestFactory = impayRequestFactory,
            client = impayWebClient
        )

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    @ConditionalOnMissingBean(ImpayCallbackResponseFactory::class)
    fun impayCallbackResponseFactory(
    ): ImpayCallbackResponseFactory =
        DefaultImpayCallbackResponseFactoryImpl(
        )

    @Bean @Qualifier(IMPAY_WEB_CALLBACK_API_BEAN_ID)
    @Order(Ordered.LOWEST_PRECEDENCE)
    @Conditional(ImpayWebCallbackApiBeanExistsCondition::class)
    fun impayCallbackApi(
        @Autowired properties: ImpayConfigurationProperties,
        @Autowired impayCallbackResponseFactory: ImpayCallbackResponseFactory
    ): RouterFunction<ServerResponse> =
        router {

            POST(properties.callbackResultPath) {
                it.bodyToMono<ResultCallbackImpayRequest>()
                    .flatMap { impayCallbackResponseFactory.makeResultCallbackResponse(Mono.just(it)) }
                    .flatMap { when(it.status) {
                        IMPAY_RESULT_CALLBACK_SUCCESS -> ServerResponse.ok()
                            .bodyValue(it)
                        else -> ServerResponse.badRequest()
                            .bodyValue(it)
                    } }
            }

            POST(properties.callbackCheckPath) {
                it.bodyToMono<CheckCallbackImpayRequest>()
                    .flatMap { impayCallbackResponseFactory.makeCheckCallbackResponse(Mono.just(it)) }
                    .flatMap {
                        ServerResponse.ok()
                            .bodyValue(it)
                    }
            }
        }
}
