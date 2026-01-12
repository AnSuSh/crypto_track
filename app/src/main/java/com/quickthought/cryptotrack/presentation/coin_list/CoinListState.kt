package com.quickthought.cryptotrack.presentation.coin_list

import com.quickthought.cryptotrack.domain.model.Coin

/**
 * State class for the CoinListScreen.
 * */
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)