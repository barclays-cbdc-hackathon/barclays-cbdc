package com.cbdc.industria.tech.bridge.enums

import net.corda.v5.base.annotations.CordaSerializable

@CordaSerializable
enum class AccessConsentStatus {
    AWAITING_AUTHORISATION, REJECTED, AUTHORISED, REVOKED
}