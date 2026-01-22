package com.quickthought.cryptotrack.domain.model

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val priceUsd: Double,
    val priceChangePercent24h: Double,
    val iconUrl: String,
    val high24h: Double = 0.0,
    val low24h: Double = 0.0,
    val marketCap: Double = 0.0,
    // New field to mark favorites in UI (default false to avoid breaking existing flows)
    val isFavorite: Boolean = false
)