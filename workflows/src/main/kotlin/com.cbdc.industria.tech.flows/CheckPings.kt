package com.cbdc.industria.tech.flows

import com.cbdc.industria.tech.bridge.services.StartCordaService
import net.corda.v5.application.flows.Flow
import net.corda.v5.application.flows.JsonConstructor
import net.corda.v5.application.flows.RpcStartFlowRequestParameters
import net.corda.v5.application.flows.StartableByRPC
import net.corda.v5.application.injection.CordaInject
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.base.concurrent.getOrThrow
import net.corda.v5.legacyapi.flows.FlowLogic


@StartableByRPC
class CheckPings @JsonConstructor constructor(private val params: RpcStartFlowRequestParameters): Flow<List<String>> {

    @CordaInject
    lateinit var startCordaService: StartCordaService

    @Suspendable
    override fun call(): List<String> {

        val publicPing = startCordaService.getPublicPing().getOrThrow().message
        val authPing = startCordaService.getAuthPing().getOrThrow().message

        return listOf(publicPing, authPing)
    }
}
