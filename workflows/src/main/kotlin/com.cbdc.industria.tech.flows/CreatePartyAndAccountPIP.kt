package com.cbdc.industria.tech.flows
//
//import com.cbdc.industria.tech.bridge.data.OpenAccountRequestBody
//import com.cbdc.industria.tech.bridge.data.RegisterPartyRequestBody
//import com.cbdc.industria.tech.bridge.enums.PartyType
//import com.cbdc.industria.tech.bridge.services.PIPCordaService
//import net.corda.v5.application.flows.Flow
//import net.corda.v5.application.flows.StartableByRPC
//import net.corda.v5.application.injection.CordaInject
//import net.corda.v5.base.annotations.CordaSerializable
//import net.corda.v5.base.annotations.Suspendable
//import net.corda.v5.base.concurrent.getOrThrow
//
//@StartableByRPC
//class CreatePartyAndAccountPIP(
//    private val envId: Long,
//    private val currencyId: Long,
//    private val pipId: Long,
//    private val partyFullLegalName: String,
//    private val partyPostalAddress: String,
//    private val partyType: PartyType
//) : Flow<PartyAndAccountIds> {
//
//    @CordaInject
//    lateinit var pipCordaService : PIPCordaService
//
//    @Suspendable
//    override fun call(): PartyAndAccountIds {
//        val pipService = pipCordaService
//        val partyId = pipService.registerPartyWithPIP(
//            xEnvId = envId,
//            xCurrencyId = currencyId,
//            pipId = pipId,
//            body = RegisterPartyRequestBody(
//                partyType = partyType,
//                partyFullLegalName = partyFullLegalName,
//                partyPostalAddress = partyPostalAddress
//            )
//        ).getOrThrow().data.id
//
//        val accountId = pipService.openAccount(
//            xEnvId = envId,
//            xCurrencyId = currencyId,
//            pipId = pipId,
//            body = OpenAccountRequestBody(partyId)
//        ).getOrThrow().data.id
//
//        return PartyAndAccountIds(partyId, accountId)
//    }
//}
//
//@CordaSerializable
//data class PartyAndAccountIds(val partyId: Long, val accountId: Long)
