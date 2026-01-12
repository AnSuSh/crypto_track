package com.quickthought.cryptotrack.data.remote

import com.quickthought.cryptotrack.BuildConfig
import com.quickthought.cryptotrack.data.remote.dto.CoinChartDto
import com.quickthought.cryptotrack.data.remote.dto.CoinDetailDto
import com.quickthought.cryptotrack.data.remote.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {
    @GET("coins/markets")
    suspend fun getCoins(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
    ): List<CoinDto>

    companion object {
        const val BASE_URL = BuildConfig.BASE_URL
    }

    @GET("coins/{id}/market_chart")
    suspend fun getCoinChart(
        @Path("id") id: String,
        @Query("vs_currency") currency: String = "usd",
        @Query("days") days: String = "7", // Fetch last 7 days
    ): CoinChartDto

    @GET("coins/{id}")
    suspend fun getCoinById(
        @Path("id") id: String
    ): CoinDetailDto
}