package com.cbdc.industria.tech.bridge.services

import com.cbdc.industria.tech.bridge.data.GetDomesticPaymentDetailsResponseBody
import com.cbdc.industria.tech.bridge.data.MakeDomesticPaymentRequestBody
import com.cbdc.industria.tech.bridge.data.MakeDomesticPaymentResponseBody
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.application.services.CordaService

@CordaSerializable
class EcosystemCordaService() : EcosystemService(
    executor = Executors.newFixedThreadPool(THREADS_COUNT),
    host = HOST_URL
)

open class EcosystemService(
    val executor: ExecutorService,
    val host: String
) : CordaService {
    fun createDomesticPayment(
        xEnvId: Long,
        currencyId: Long,
        body: MakeDomesticPaymentRequestBody
    ): Future<MakeDomesticPaymentResponseBody> {
        val future = CompletableFuture<MakeDomesticPaymentResponseBody>()

        executor.execute {
            val result = makePostRequest<MakeDomesticPaymentResponseBody>(
                url = "$host/$ECOSYSTEMS",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to currencyId,
                    CONTENT_TYPE to APPLICATION_JSON
                ),
                body = jacksonObjectMapper().writeValueAsBytes(body)
            )
            result.toCompletableFuture(future)
        }

        return future
    }

    fun getDomesticPayment(
        xEnvId: Long,
        currencyId: Long,
        domesticPaymentId: Long
    ): Future<GetDomesticPaymentDetailsResponseBody> {
        val future = CompletableFuture<GetDomesticPaymentDetailsResponseBody>()

        executor.execute {
            val result = makeGetRequest<GetDomesticPaymentDetailsResponseBody>(
                url = "$host/$ECOSYSTEMS/$domesticPaymentId",
                headers = mapOf(
                    AUTH_HEADER_KEY to AUTH_TOKEN,
                    X_ENV_ID to xEnvId,
                    X_CURRENCY_ID to currencyId
                )
            )
            result.toCompletableFuture(future)
        }

        return future
    }
}