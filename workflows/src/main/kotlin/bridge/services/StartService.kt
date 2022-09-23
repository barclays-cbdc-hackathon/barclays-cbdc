package com.cbdc.industria.tech.bridge.services

import net.corda.v5.application.injection.CordaFlowInjectable
import net.corda.v5.application.injection.CordaServiceInjectable
import net.corda.v5.application.services.CordaService
import net.corda.v5.base.annotations.CordaSerializable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future


class StartCordaService() : StartService(
    executor = Executors.newFixedThreadPool(THREADS_COUNT),
    host = HOST_URL
)

open class StartService(
    private val executor: ExecutorService,
    private val host: String
) : CordaService, CordaFlowInjectable, CordaServiceInjectable {

    fun getPublicPing(): Future<PingResponse> {
        val future = CompletableFuture<PingResponse>()

        executor.execute {
            val result = makeGetRequest<PingResponse>(url = "$host/start-here/public-ping", mapOf())
            result.toCompletableFuture(future)
        }

        return future
    }

    fun getAuthPing(): Future<PingResponse> {
        val future = CompletableFuture<PingResponse>()

        executor.execute {
            val result = makeGetRequest<PingResponse>(
                url = "$host/start-here/auth-ping",
                headers = mapOf(AUTH_HEADER_KEY to AUTH_TOKEN)
            )
            result.toCompletableFuture(future)
        }

        return future
    }
}

@CordaSerializable
data class PingResponse(val message: String)
