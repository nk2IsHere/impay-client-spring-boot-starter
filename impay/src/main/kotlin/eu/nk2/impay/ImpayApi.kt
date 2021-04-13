package eu.nk2.impay


sealed class ImpayApiHost(
    val scheme: String,
    val host: String,
    val port: Int = -1
) {

    object ImpayApiProductionHost: ImpayApiHost("https", "api.impay.ru")
    object ImpayApiDebugHost: ImpayApiHost("https", "test.impay.ru", 806)
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

    class ImpayApiDebugCredentials(
        id: String,
        token: String,
    ): ImpayApiCredentials(
        id = id,
        token = token
    )
}

sealed class ImpayApiFlavour(
    val path: List<String>,
) {

    object ImpayApiV1Flavour: ImpayApiFlavour(path = listOf("v1"))
}

data class ImpayApiMethod(
    val path: List<String>,
    val flavour: ImpayApiFlavour
) {
    constructor(path: String, flavour: ImpayApiFlavour): this(path = path.split('/'), flavour)
}

data class ImpayApi(
    val host: ImpayApiHost,
    val credentials: ImpayApiCredentials,
    val timeoutMillis: Long = 5000L,
    val attempts: Long = 5,
)
