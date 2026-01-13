package com.quickthought.cryptotrack.data.remote.dto

import androidx.annotation.Keep

@Keep
data class CoinChartDto(
    val prices: List<List<Double>>
)
