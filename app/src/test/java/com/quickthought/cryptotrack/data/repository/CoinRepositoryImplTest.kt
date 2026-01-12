package com.quickthought.cryptotrack.data.repository

import app.cash.turbine.test
import com.quickthought.cryptotrack.core.Resource
import com.quickthought.cryptotrack.data.remote.CoinGeckoApi
import com.quickthought.cryptotrack.data.remote.dto.CoinDto
import com.quickthought.cryptotrack.domain.model.Coin
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CoinRepositoryImplTest {

    private lateinit var repository: CoinRepositoryImpl
    private lateinit var mockApi: CoinGeckoApi

    @Before
    fun setUp() {
        mockApi = mockk()
        repository = CoinRepositoryImpl(mockApi)
    }

    @Test
    fun `getCoins emits Loading then Success when API call is successful`() = runTest {
        // 1. Setup mock API response
        val mockCoins = emptyList<CoinDto>()
        coEvery { mockApi.getCoins() } returns mockCoins

        // 2. Call repository.getCoins()
        val result = repository.getCoins()

        // 3. Use Turbine to "awaitItem" and check for Resource.Loading
        result.test {
            val loadingResult = awaitItem()
            assertTrue(loadingResult is Resource.Loading)

            val successResult = awaitItem()
            assertTrue(successResult is Resource.Success)
            assertEquals(successResult.data, emptyList<Coin>())

            cancelAndIgnoreRemainingEvents()
        }
    }
}