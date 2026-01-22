package com.quickthought.cryptotrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.quickthought.cryptotrack.data.local.entity.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)

    @Query("DELETE FROM coin_favorites WHERE coinId = :coinId")
    suspend fun removeFavorite(coinId: String)

    @Query("SELECT coinId FROM coin_favorites")
    fun getAllFavoriteIds(): Flow<List<String>>
}