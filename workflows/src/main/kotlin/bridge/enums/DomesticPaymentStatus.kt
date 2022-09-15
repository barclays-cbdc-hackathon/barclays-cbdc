package com.cbdc.industria.tech.bridge.enums

import net.corda.v5.base.annotations.CordaSerializable

@CordaSerializable
enum class DomesticPaymentStatus {
    CREATED, SETTLEMENT_IN_PROGRESS, SETTLEMENT_IN_PROGRESS_SOURCE_ACCOUNT_DEBITED, COMPLETE, FAILED
}