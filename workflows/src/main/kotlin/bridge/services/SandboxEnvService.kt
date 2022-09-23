package com.cbdc.industria.tech.bridge.services
//
//import com.cbdc.industria.tech.bridge.data.CreateNewEnvironmentResponseBody
//import com.cbdc.industria.tech.bridge.data.GetEnvironmentDetailsPageResponseBody
//import com.cbdc.industria.tech.bridge.data.GetEnvironmentDetailsResponseBody
//import net.corda.v5.application.injection.CordaFlowInjectable
//import net.corda.v5.application.injection.CordaServiceInjectable
//import net.corda.v5.application.services.CordaService
//import java.util.concurrent.CompletableFuture
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//import java.util.concurrent.Future
//
//class SandboxEnvCordaService() : SandboxEnvService(
//    executor = Executors.newFixedThreadPool(THREADS_COUNT),
//    host = HOST_URL
//)
//
//open class SandboxEnvService(
//    private val executor: ExecutorService,
//    private val host: String
//): CordaService, CordaFlowInjectable, CordaServiceInjectable {
//
//    fun getEnvs(
//        pageIndex: Int = 0,
//        pageSize: Int = 20
//    ): Future<GetEnvironmentDetailsPageResponseBody> {
//        if (pageSize > MAX_PAGE_SIZE)
//            throw IllegalArgumentException("pageSize must not be grater than $MAX_PAGE_SIZE.")
//
//        val future = CompletableFuture<GetEnvironmentDetailsPageResponseBody>()
//
//        executor.execute {
//            val result = makeGetRequest<GetEnvironmentDetailsPageResponseBody>(
//                url = "$host/envs",
//                headers = mapOf(AUTH_HEADER_KEY to AUTH_TOKEN),
//                params = listOf(Pair(PAGE_INDEX_KEY, pageIndex), Pair(PAGE_SIZE_KEY, pageSize))
//            )
//            result.toCompletableFuture(future)
//        }
//
//        return future
//    }
//
//    fun postEnv(): Future<CreateNewEnvironmentResponseBody> {
//        val future = CompletableFuture<CreateNewEnvironmentResponseBody>()
//
//        executor.execute {
//            val result = makePostRequest<CreateNewEnvironmentResponseBody>(
//                url = "$host/envs",
//                headers = mapOf(AUTH_HEADER_KEY to AUTH_TOKEN),
//                body = ByteArray(0)
//            )
//            result.toCompletableFuture(future)
//        }
//
//        return future
//    }
//
//    fun getEnv(
//        envId: Long
//    ): Future<CreateNewEnvironmentResponseBody> {
//        val future = CompletableFuture<CreateNewEnvironmentResponseBody>()
//
//        executor.execute {
//            val result = makeGetRequest<CreateNewEnvironmentResponseBody>(
//                url = "$host/envs/$envId",
//                headers = mapOf(AUTH_HEADER_KEY to AUTH_TOKEN)
//            )
//            result.toCompletableFuture(future)
//        }
//
//        return future
//    }
//
//    fun deleteEnv(
//        envId: Long
//    ): Future<GetEnvironmentDetailsResponseBody> {
//        val future = CompletableFuture<GetEnvironmentDetailsResponseBody>()
//
//        executor.execute {
//            val result = makeDeleteRequest<GetEnvironmentDetailsResponseBody>(
//                url = "$host/envs/$envId",
//                headers = mapOf(AUTH_HEADER_KEY to AUTH_TOKEN)
//            )
//            result.toCompletableFuture(future)
//        }
//
//        return future
//    }
//}
