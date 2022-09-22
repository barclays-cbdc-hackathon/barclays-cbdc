package com.cbdc.industria.tech.bridge.models

import net.corda.v5.base.annotations.CordaSerializable

@CordaSerializable
data class CurrencyChild(val currencyId: Long, val status: String, val currency: Currency)

@CordaSerializable
data class Currency(val code: String, val numDecimalDigits: Int)
