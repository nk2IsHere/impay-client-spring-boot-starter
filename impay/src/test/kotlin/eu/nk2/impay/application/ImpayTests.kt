package eu.nk2.impay.application

import eu.nk2.impay.ImpayClient
import eu.nk2.impay.common.ImpayCardType
import eu.nk2.impay.request.CardRegImpayRequest
import eu.nk2.impay.request.PayStartImpayRequest
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [ImpayApplication::class])
class ImpayTests(
    @Autowired private val impayClient: ImpayClient
) {

    private val log = LoggerFactory.getLogger(ImpayTests::class.java)

    @Test fun generalApiTest() {
        val response = impayClient.makeRequest(CardRegImpayRequest(
            type = ImpayCardType.InCardType,
            extId = "aaa",
            timeout = 30
        )).block()

        log.info("cardreg: $response")
    }

    @Test fun amountApiTest() {
        val response = impayClient.makeRequest(PayStartImpayRequest(
            documentId = "aaa",
            amount = 100.toBigDecimal(),
            fullName = "Test Test",
            extId = "aaa",
            timeout = 30,
            cardNumber = "4300000000000777", //Thanks, dengoff!
            cardHolder = "Test Test",
            cardYear = "22",
            cardMonth = "12",
            cardCvc = "111"
        )).block()

        log.info("paystart: $response")
    }
}
