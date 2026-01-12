package com.quickthought.cryptotrack.core.di

import com.quickthought.cryptotrack.BuildConfig
import com.quickthought.cryptotrack.data.remote.CoinGeckoApi
import com.quickthought.cryptotrack.data.repository.CoinRepositoryImpl
import com.quickthought.cryptotrack.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url

                // Injecting the API key as a Query Parameter (CoinGecko standard)
                val url = originalUrl.newBuilder()
                    .addQueryParameter("x_cg_demo_api_key", BuildConfig.CG_API_KEY)
                    .build()

                val request = originalRequest.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideCoinGeckoApi(client: OkHttpClient): CoinGeckoApi {
        return Retrofit.Builder()
            .baseUrl(CoinGeckoApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CoinGeckoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinGeckoApi): CoinRepository {
        return CoinRepositoryImpl(api)
    }
}