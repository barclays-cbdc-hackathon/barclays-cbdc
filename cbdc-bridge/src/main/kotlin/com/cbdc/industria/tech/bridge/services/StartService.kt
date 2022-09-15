package com.cbdc.industria.tech.bridge.services

import net.corda.v5.application.services.CordaService
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
) : CordaService {

    fun getPublicPing(): Future<String> {
        val future = CompletableFuture<String>()

        executor.execute {
            val result = makeGetRequest<String>(url = "$host/start-here/public-ping", mapOf())
            result.toCompletableFuture(future)
        }

        return future
    }

    fun getAuthPing(): Future<String> {
        val future = CompletableFuture<String>()

        executor.execute {
            val result = makeGetRequest<String>(
                url = "$host/start-here/public-ping",
                headers = mapOf(AUTH_HEADER_KEY to AUTH_TOKEN)
            )
            result.toCompletableFuture(future)
        }

        return future
    }
}
