package com.quickthought.cryptotrack.data.mapper

import com.quickthought.cryptotrack.data.remote.dto.CoinChartDto
import com.quickthought.cryptotrack.data.remote.dto.CoinDetailDto
import com.quickthought.cryptotrack.domain.model.ChartData
import com.quickthought.cryptotrack.domain.model.Coin

fun CoinDetailDto.toCoin(): Coin {
    return Coin(
        id = id,
        name = name,
        symbol = symbol.uppercase(),
        priceUsd = marketData.currentPrice["usd"] ?: 0.0,
        priceChangePercent24h = marketData.priceChange24h,
        iconUrl = image.large,
        marketCap = marketData.marketCap["usd"] ?: 0.0,
        high24h = marketData.high24h["usd"] ?: 0.0,
        low24h = marketData.low24h["usd"] ?: 0.0
    )
}

// Extension function to map to our clean Domain Model
fun CoinChartDto.toChartData(): ChartData {
    return ChartData(
        yValues = prices.map { it[1].toFloat() } // We only need the prices for the Y-axis
    )
}