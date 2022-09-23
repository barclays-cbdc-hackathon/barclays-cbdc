package com.cbdc.industria.tech.flows
//
//import com.cbdc.industria.tech.bridge.services.PIPCordaService
//import net.corda.v5.application.flows.Flow
//import net.corda.v5.application.flows.StartableByRPC
//import net.corda.v5.application.injection.CordaInject
//import net.corda.v5.base.annotations.Suspendable
//import net.corda.v5.base.concurrent.getOrThrow
//
//@StartableByRPC
//class DeleteAccountAndPartyPIP(
//    private val accountAndPartyIds: PartyAndAccountIds,
//    private val envId: Long,
//    private val currencyId: Long,
//    private val pipId: Long
//) : Flow<Unit> {
//
//    @CordaInject
//    lateinit var pipCordaService : PIPCordaService
//
//    @Suspendable
//    override fun call() {
//        pipCordaService.deletePIPAccount(envId, currencyId, pipId, accountAndPartyIds.accountId).getOrThrow()
//        pipCordaService.deletePIPParty(envId, currencyId, pipId, accountAndPartyIds.partyId).getOrThrow()
//    }
//}