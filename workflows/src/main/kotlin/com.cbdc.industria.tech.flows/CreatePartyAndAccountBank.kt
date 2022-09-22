package com.cbdc.industria.tech.flows

import com.cbdc.industria.tech.bridge.data.OpenAccountRequestBody
import com.cbdc.industria.tech.bridge.data.RegisterPartyRequestBody
import com.cbdc.industria.tech.bridge.enums.PartyType
import com.cbdc.industria.tech.bridge.services.CommercialBankCordaService
import net.corda.v5.application.flows.Flow
import net.corda.v5.application.flows.StartableByRPC
import net.corda.v5.application.injection.CordaInject
import net.corda.v5.base.annotations.Suspendable
import net.corda.v5.base.concurrent.getOrThrow

@StartableByRPC
class CreatePartyAndAccountBank(
    private val envId: Long,
    private val currencyId: Long,
    private val bankId: Long,
    private val partyFullLegalName: String,
    private val partyPostalAddress: String,
    private val partyType: PartyType
) : Flow<PartyAndAccountIds> {

    @CordaInject
    lateinit var commercialBankCordaService: CommercialBankCordaService

    @Suspendable
    override fun call(): PartyAndAccountIds {
        val commercialBank = commercialBankCordaService
        val partyId = commercialBank.registerPartyWithBank(
            xEnvId = envId,
            xCurrencyId = currencyId,
            bankId = bankId,
            body = RegisterPartyRequestBody(
                partyType = partyType,
                partyFullLegalName = partyFullLegalName,
                partyPostalAddress = partyPostalAddress
            )
        ).getOrThrow().data.id

        val accountId = commercialBank.openAccount(
            xEnvId = envId,
            xCurrencyId = currencyId,
            bankId = bankId,
            body = OpenAccountRequestBody(partyId)
        ).getOrThrow().data.id

        return PartyAndAccountIds(partyId, accountId)
    }
}
