package com.cbdc.industria.tech.flows

import com.cbdc.industria.tech.bridge.services.CommercialBankCordaService
import net.corda.v5.application.flows.Flow
import net.corda.v5.application.flows.StartableByRPC
import net.corda.v5.application.injection.CordaInject
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.base.concurrent.getOrThrow

@StartableByRPC
class DeleteAccountAndPartyBank(
    private val accountAndPartyIds: PartyAndAccountIds,
    private val envId: Long,
    private val currencyId: Long,
    private val bankId: Long
) : Flow<Unit> {

    @CordaInject
    lateinit var commercialBankCordaService: CommercialBankCordaService

    @Suspendable
    override fun call() {
        commercialBankCordaService.deleteAccount(envId, currencyId, bankId, accountAndPartyIds.accountId).getOrThrow()
        commercialBankCordaService.deleteParty(envId, currencyId, bankId, accountAndPartyIds.partyId).getOrThrow()
    }
}