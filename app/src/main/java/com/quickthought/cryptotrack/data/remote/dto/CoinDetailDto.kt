package com.quickthought.cryptotrack.data.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CoinDetailDto(
    val id: String,
    val name: String,
    val symbol: String,
    val image: ImageSource,
    @SerializedName("market_data") val marketData: MarketData
)

@Keep
data class ImageSource(val large: String)

@Keep
data class MarketData(
    @SerializedName("current_price") val currentPrice: Map<String, Double>,
    @SerializedName("price_change_percentage_24h") val priceChange24h: Double,
    @SerializedName("market_cap") val marketCap: Map<String, Double>,
    @SerializedName("high_24h") val high24h: Map<String, Double>,
    @SerializedName("low_24h") val low24h: Map<String, Double>
)