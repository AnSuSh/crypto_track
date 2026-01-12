package com.quickthought.cryptotrack.domain.use_case.get_coin_detail

import com.quickthought.cryptotrack.core.Resource
import com.quickthought.cryptotrack.domain.model.CoinFullDetail
import com.quickthought.cryptotrack.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    /**
     * Fetches the full details for a specific cryptocurrency, including its general information
     * and historical chart data.
     *
     * This function makes two concurrent API calls: one to get the coin's details and another
     * to get its chart data. It then combines the results into a single [CoinFullDetail] object.
     * The entire process is wrapped in a [Flow] that emits [Resource] states to represent
     * loading, success, or error.
     *
     * @param coinId The unique identifier of the coin (e.g., "bitcoin").
     * @return A [Flow] emitting [Resource] states.
     *         - [Resource.Loading] is emitted while the data is being fetched.
     *         - [Resource.Success] is emitted with the [CoinFullDetail] data if both API calls are successful.
     *         - [Resource.Error] is emitted if either of the API calls fails or an unexpected exception occurs.
     */
    operator fun invoke(coinId: String): Flow<Resource<CoinFullDetail>> =
        repository.getCoinById(coinId).zip(repository.getCoinChart(coinId)) { coinRes, chartRes ->
            when {
                coinRes is Resource.Loading || chartRes is Resource.Loading -> Resource.Loading()
                coinRes is Resource.Success && chartRes is Resource.Success -> {
                    Resource.Success(CoinFullDetail(coinRes.data!!, chartRes.data!!))
                }

                coinRes is Resource.Error -> Resource.Error(coinRes.message ?: "Error")
                else -> Resource.Error(chartRes.message ?: "Error")
            }
        }.catch { e ->
            emit(Resource.Error(e.message ?: "Unexpected Error"))
        }
}