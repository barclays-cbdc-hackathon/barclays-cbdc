package com.cbdc.industria.tech.bridge.services

import net.corda.v5.application.injection.CordaFlowInjectable
import net.corda.v5.application.injection.CordaServiceInjectable
import net.corda.v5.application.services.CordaService
import net.corda.v5.base.annotations.CordaSerializable
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future


class StartServiceImpl : StartService {
    override val executor: ExecutorService = Executors.newFixedThreadPool(THREADS_COUNT)
    override val host: String = HOST_URL

    override fun getPublicPing(): Future<PingResponse> {
        val future = CompletableFuture<PingResponse>()

        executor.execute {
            val result = makeGetRequest<PingResponse>(url = "$host/start-here/public-ping", mapOf())
            result.toCompletableFuture(future)
        }

        return future
    }

    override fun getAuthPing(): Future<PingResponse> {
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

    override fun getHttp(): String {
        val client = OkHttpClient()
        val request = Request.Builder().url("${HOST_URL}/start-here/public-ping").build()

        return with(client.newCall(request).execute()) {
            this.body?.string() ?: ""
        }
//        return ""
    }
}

interface StartService : CordaService, CordaFlowInjectable, CordaServiceInjectable {

    val executor: ExecutorService
    val host: String

    fun getPublicPing(): Future<PingResponse>

    fun getAuthPing(): Future<PingResponse>

    fun getHttp(): String
}

@CordaSerializable
data class PingResponse(val message: String)
