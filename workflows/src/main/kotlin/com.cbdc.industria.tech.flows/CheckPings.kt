package com.cbdc.industria.tech.flows

import com.cbdc.industria.tech.bridge.services.StartService
import net.corda.v5.application.flows.Flow
import net.corda.v5.application.flows.JsonConstructor
import net.corda.v5.application.flows.RpcStartFlowRequestParameters
import net.corda.v5.application.flows.StartableByRPC
import net.corda.v5.application.injection.CordaInject
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.base.concurrent.getOrThrow
import net.corda.v5.base.util.contextLogger


@StartableByRPC
class CheckPings @JsonConstructor constructor(private val params: RpcStartFlowRequestParameters): Flow<List<String>> {

    private companion object {
        private val logger = contextLogger()
    }

    @CordaInject
    lateinit var startCordaService: StartService

    @Suspendable
    override fun call(): List<String> {

        val publicPing = startCordaService.getPublicPing().getOrThrow().message
        val authPing = startCordaService.getAuthPing().getOrThrow().message

        logger.info(publicPing)
        logger.info(authPing)

//        val okping = startCordaService.getHttp()
//
//        logger.info(okping)
//
//        return listOf(okping)
        return listOf("empty")
//        return listOf(publicPing, authPing)
    }
}
