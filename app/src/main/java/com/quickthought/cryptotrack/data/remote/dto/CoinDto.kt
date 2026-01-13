package com.quickthought.cryptotrack.data.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CoinDto(
    val id: String,
    val name: String,
    val symbol: String,
    @SerializedName("current_price") val currentPrice: Double,
    @SerializedName("price_change_percentage_24h") val priceChange24h: Double,
    @SerializedName("image") val imageUrl: String
)
