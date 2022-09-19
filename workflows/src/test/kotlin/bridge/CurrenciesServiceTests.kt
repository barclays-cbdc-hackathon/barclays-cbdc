package com.cbdc.industria.tech.bridge

import com.cbdc.industria.tech.bridge.enums.CurrencyCode
import com.cbdc.industria.tech.bridge.services.CurrencyService
import com.cbdc.industria.tech.bridge.services.HOST_URL
import com.cbdc.industria.tech.bridge.services.SandboxEnvService
import com.cbdc.industria.tech.bridge.services.THREADS_COUNT
import net.corda.v5.base.concurrent.getOrThrow
import java.util.concurrent.Executors
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CurrenciesServiceTests {
    private val sandboxEnvService = SandboxEnvService(
        executor = Executors.newFixedThreadPool(THREADS_COUNT),
        host = HOST_URL
    )

    private val currenciesService = CurrencyService(
        executor = Executors.newFixedThreadPool(THREADS_COUNT),
        host = HOST_URL
    )

    private var environmentId: Long = 0
    private var currencyId: Long = 0

    @BeforeEach
    fun setup() {
        environmentId = sandboxEnvService.createEnvironment()
        currencyId = currenciesService.createCurrency(CurrencyCode.EUR, environmentId)
    }

    @AfterEach
    fun tearDown() {
        currenciesService.deleteCurrency(xEnvId = environmentId, currencyId = currencyId).getOrThrow()
        sandboxEnvService.deleteEnv(environmentId).getOrThrow()
    }

    @Test
    fun `create currency` () {
        assertThat(currenciesService.getAllCurrencies(environmentId).size).isEqualTo(1)
    }

    @Test
    fun `get currency` () {
        val currencyFuture = currenciesService.getCurrency(xEnvId = environmentId, currencyId = currencyId)
        val currency = currencyFuture.getOrThrow().data
        assertThat(currency.id).isEqualTo(currencyId)
    }
}