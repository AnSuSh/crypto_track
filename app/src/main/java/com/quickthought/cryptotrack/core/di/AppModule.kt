package com.quickthought.cryptotrack.core.di

import android.app.Application
import androidx.room.Room
import com.quickthought.cryptotrack.data.local.CoinDatabase
import com.quickthought.cryptotrack.data.local.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoinDatabase(app: Application): CoinDatabase {
        return Room.databaseBuilder(
            app,
            CoinDatabase::class.java,
            CoinDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(database: CoinDatabase): FavoriteDao {
        return database.favoriteDao()
    }
}