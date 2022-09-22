package com.cbdc.industria.tech.flows

import com.cbdc.industria.tech.bridge.services.CommercialBankCordaService
import com.cbdc.industria.tech.bridge.services.CurrencyCordaService
import com.cbdc.industria.tech.bridge.services.PIPCordaService
import com.cbdc.industria.tech.bridge.services.SandboxEnvCordaService
import net.corda.v5.application.flows.Flow
import net.corda.v5.application.flows.StartableByRPC
import net.corda.v5.application.injection.CordaInject
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.base.concurrent.getOrThrow

@StartableByRPC
/**
 * Delete logical test environment
 * Delete Currency
 * Delete commercial banks
 * Delete Payment Interface Providers (PIPs)
 */
class DeleteLogicalResources(private val logicalResources: LogicalResources) : Flow<Unit> {

    @CordaInject
    lateinit var sandBoxService : SandboxEnvCordaService

    @CordaInject
    lateinit var currencyCordaService : CurrencyCordaService

    @CordaInject
    lateinit var commercialBankCordaService : CommercialBankCordaService

    @CordaInject
    lateinit var pipCordaService : PIPCordaService

    @Suspendable
    override fun call() {
        val envId = logicalResources.envId
        val currencyId = logicalResources.currencyId

        pipCordaService.deletePIP(envId, currencyId, logicalResources.pipId).getOrThrow()
        commercialBankCordaService.deleteCommercialBank(envId, currencyId, logicalResources.bankId).getOrThrow()
        currencyCordaService.deleteCurrency(envId, currencyId).getOrThrow()
        sandBoxService.deleteEnv(envId).getOrThrow()
    }
}