package com.quickthought.cryptotrack.data.repository

import com.quickthought.cryptotrack.core.Resource
import com.quickthought.cryptotrack.data.mapper.toChartData
import com.quickthought.cryptotrack.data.mapper.toCoin
import com.quickthought.cryptotrack.data.remote.CoinGeckoApi
import com.quickthought.cryptotrack.domain.model.ChartData
import com.quickthought.cryptotrack.domain.model.Coin
import com.quickthought.cryptotrack.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val api: CoinGeckoApi) : CoinRepository {

    override fun getCoins(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())

            val coins = api.getCoins().map { it.toCoin() }

            emit(Resource.Success(coins))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (_: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override fun getCoinChart(coinId: String): Flow<Resource<ChartData>> = flow {
        try {
            emit(Resource.Loading())
            val chartData = api.getCoinChart(id = coinId).toChartData()
            emit(Resource.Success(chartData))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load chart: ${e.message}"))
        }
    }

    override fun getCoinById(coinId: String): Flow<Resource<Coin>> = flow {
        emit(Resource.Loading())
        try {
            val coin = api.getCoinById(coinId).toCoin()
            emit(Resource.Success(coin))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }
}