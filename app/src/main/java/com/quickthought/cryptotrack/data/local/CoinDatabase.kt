package com.quickthought.cryptotrack.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quickthought.cryptotrack.data.local.entity.Favorite

@Database(
    entities = [Favorite::class],
    version = 1,
    exportSchema = true,
)
abstract class CoinDatabase: RoomDatabase() {
    abstract fun favoriteDao() : FavoriteDao

    companion object {
        const val DATABASE_NAME = "crypto_coins_db"
    }
}