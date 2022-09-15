package com.cbdc.industria.tech.bridge.models

import com.cbdc.industria.tech.bridge.enums.PartyStatus
import net.corda.v5.base.annotations.CordaSerializable

@CordaSerializable
data class RegisteredParty(val partyId: Long, val status: PartyStatus)
