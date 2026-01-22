package com.quickthought.cryptotrack.domain.use_case.favorites

import com.quickthought.cryptotrack.data.local.FavoriteDao
import com.quickthought.cryptotrack.data.local.entity.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteIdsUseCase @Inject constructor(private val dao: FavoriteDao) {
    operator fun invoke(): Flow<List<String>> = dao.getAllFavoriteIds()
}

class AddFavoriteUseCase @Inject constructor(private val dao: FavoriteDao) {
    suspend operator fun invoke(coinId: String) = dao.addFavorite(Favorite(coinId))
}

class RemoveFavoriteUseCase @Inject constructor(private val dao: FavoriteDao) {
    suspend operator fun invoke(coinId: String) = dao.removeFavorite(coinId)
}
