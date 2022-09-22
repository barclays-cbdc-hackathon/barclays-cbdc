package com.cbdc.industria.tech.flows

import com.cbdc.industria.tech.bridge.data.CreateCommercialBankRequestBody
import com.cbdc.industria.tech.bridge.data.CreateCurrencyRequestBody
import com.cbdc.industria.tech.bridge.data.CreatePaymentInterfaceProviderRequestBody
import com.cbdc.industria.tech.bridge.enums.CurrencyCode
import com.cbdc.industria.tech.bridge.services.CommercialBankCordaService
import com.cbdc.industria.tech.bridge.services.CurrencyCordaService
import com.cbdc.industria.tech.bridge.services.PIPCordaService
import com.cbdc.industria.tech.bridge.services.SandboxEnvCordaService
import net.corda.v5.application.flows.Flow
import net.corda.v5.application.flows.StartableByRPC
import net.corda.v5.application.injection.CordaInject
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.base.concurrent.getOrThrow

@StartableByRPC
/**
 * Create logical test environment
 * Create Currency
 * Create commercial banks
 * Create Payment Interface Providers (PIPs)
 */
class CreateLogicalResources(
    private val commercialBankName: String,
    private val pipName: String
) : Flow<LogicalResources> {

    @CordaInject
    lateinit var sandBoxService : SandboxEnvCordaService

    @CordaInject
    lateinit var currencyCordaService : CurrencyCordaService

    @CordaInject
    lateinit var commercialBankCordaService : CommercialBankCordaService

    @CordaInject
    lateinit var pipCordaService : PIPCordaService

    @Suspendable
    override fun call(): LogicalResources {
        val envId = sandBoxService.postEnv().getOrThrow().data.id

        val currencyId = currencyCordaService.postCurrency(
            xEnvId = envId,
            body = CreateCurrencyRequestBody(
                currencyCode = CurrencyCode.EUR
            )
        ).getOrThrow().data.id

        val commercialBankId = commercialBankCordaService
            .createCommercialBank(
                xEnvId = envId,
                xCurrencyId = currencyId,
                body = CreateCommercialBankRequestBody(
                    commercialBankName = commercialBankName
                )
            ).getOrThrow().data.id

        val pipId = pipCordaService
            .createPIP(
                xEnvId = envId,
                xCurrencyId = currencyId,
                body = CreatePaymentInterfaceProviderRequestBody(pipName = pipName)
            ).getOrThrow().data.id

        return LogicalResources(envId, currencyId, commercialBankId, pipId)
    }
}

@CordaSerializable
data class LogicalResources(
    val envId: Long,
    val currencyId: Long,
    val bankId: Long,
    val pipId: Long
)
