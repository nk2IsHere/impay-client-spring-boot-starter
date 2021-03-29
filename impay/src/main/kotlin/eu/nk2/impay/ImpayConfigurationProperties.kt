package eu.nk2.impay

import eu.nk2.donate.utils.NoArgsConstructor
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

private const val IMPAY_PROPERTIES_PREFIX = "impay"

@NoArgsConstructor
@ConstructorBinding
@Component
@ConfigurationProperties(prefix = IMPAY_PROPERTIES_PREFIX)
data class ImpayConfigurationProperties(
    val hostMode: ImpayConfigurationPropertiesHostMode = ImpayConfigurationPropertiesHostMode.DEBUG,
    val credentialsMode: ImpayConfigurationPropertiesCredentialsMode = ImpayConfigurationPropertiesCredentialsMode.DEBUG,
    val merchantId: String?,
    val merchantToken: String?,
    val merchantCallbackToken: String?,
    val timeoutMillis: Long = 5000L,
    val attempts: Long = 5,
    val callbackResultPath: String = "/impay/callback/result",
    val callbackCheckPath: String = "/impay/callback/check",
) {

    enum class ImpayConfigurationPropertiesHostMode {
        DEBUG,
        PRODUCTION
    }

    enum class ImpayConfigurationPropertiesCredentialsMode {
        DEBUG,
        PRODUCTION
    }
}
