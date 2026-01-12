package com.quickthought.cryptotrack.data.mapper

import com.quickthought.cryptotrack.data.remote.dto.CoinDto
import org.junit.Before
import org.junit.Test

class CoinMapperTest {

    private lateinit var fakeDto: CoinDto

    @Before
    fun setUp() {
        // 1. Arrange: Create a fake DTO
        fakeDto = CoinDto(
            id = "bitcoin",
            name = "Bitcoin",
            symbol = "BTC",
            currentPrice = 40000.0,
            priceChange24h = 1000.0,
            imageUrl = "https://example.com/bitcoin.png"
        )
    }

    @Test
    fun `Map CoinDto to Coin correctly`() {
        // 2. Act: Run the mapper
        val domainModel = fakeDto.toCoin()

        assert(domainModel.id == "bitcoin")
        assert(domainModel.symbol == "BTC")
        assert(domainModel.name == "Bitcoin")
        assert(domainModel.priceUsd == 40000.0)
        assert(domainModel.priceChangePercent24h == 1000.0)
        assert(domainModel.iconUrl == "https://example.com/bitcoin.png")
    }
}