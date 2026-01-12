package com.quickthought.cryptotrack.data.mapper

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.quickthought.cryptotrack.data.remote.dto.CoinDto
import com.quickthought.cryptotrack.domain.model.Coin

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        name = name,
        symbol = symbol.toUpperCase(Locale.current),
        priceUsd = currentPrice,
        priceChangePercent24h = priceChange24h,
        iconUrl = imageUrl,
    )
}