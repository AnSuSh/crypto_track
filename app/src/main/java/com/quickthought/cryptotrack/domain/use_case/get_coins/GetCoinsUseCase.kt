package com.quickthought.cryptotrack.domain.use_case.get_coins

import com.quickthought.cryptotrack.core.Resource
import com.quickthought.cryptotrack.domain.model.Coin
import com.quickthought.cryptotrack.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    // We overload the invoke operator so we can call getCoinsUseCase() like a function
    operator fun invoke(): Flow<Resource<List<Coin>>> {
        return repository.getCoins()
    }
}