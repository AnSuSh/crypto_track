package com.quickthought.cryptotrack.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_favorites")
data class Favorite(
    @PrimaryKey val coinId: String
)
