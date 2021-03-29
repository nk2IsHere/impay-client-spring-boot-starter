package eu.nk2.impay


sealed class ImpayApiHost(
    val scheme: String,
    val host: String
) {

    object ImpayApiProductionHost: ImpayApiHost("https", "api.impay.ru")
    object ImpayApiDebugHost: ImpayApiHost("https", "test.impay.ru")
}

sealed class ImpayApiCredentials(
    val id: String,
    val token: String,
) {

    class ImpayApiProductionCredentials(
        id: String,
        token: String,
    ): ImpayApiCredentials(
        id = id,
        token = token
    )

    object ImpayApiDemoCredentials: ImpayApiCredentials(
        id = TODO(),
        token = TODO(),
    )
}

sealed class ImpayApiFlavour(
    val path: String,
) {

    object ImpayApiV1Flavour: ImpayApiFlavour(path = "/v1")
}

data class ImpayApiMethod(
    val path: String,
    val flavour: ImpayApiFlavour
)

data class ImpayApi(
    val host: ImpayApiHost,
    val credentials: ImpayApiCredentials,
    val timeoutMillis: Long = 5000L,
    val attempts: Long = 5,
)
