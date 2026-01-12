package com.quickthought.cryptotrack.domain.repository

import com.quickthought.cryptotrack.core.Resource
import com.quickthought.cryptotrack.domain.model.ChartData
import com.quickthought.cryptotrack.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    fun getCoins(): Flow<Resource<List<Coin>>>

    fun getCoinChart(coinId: String): Flow<Resource<ChartData>>

    fun getCoinById(coinId: String): Flow<Resource<Coin>>
}