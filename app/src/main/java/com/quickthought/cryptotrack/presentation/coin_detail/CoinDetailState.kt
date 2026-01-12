package com.quickthought.cryptotrack.presentation.coin_detail

import com.quickthought.cryptotrack.domain.model.CoinFullDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coinDetail: CoinFullDetail? = null,
    val error: String = ""
)