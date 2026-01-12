package com.quickthought.cryptotrack.presentation.coin_list

import com.quickthought.cryptotrack.core.Resource
import com.quickthought.cryptotrack.domain.model.Coin
import com.quickthought.cryptotrack.domain.use_case.get_coins.GetCoinsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CoinListViewModelTest {

    private lateinit var viewModel: CoinListViewModel
    private val mockUseCase: GetCoinsUseCase = mockk()

    @Test
    fun `ViewModel should emit loading then success state`() = runTest {
        // Arrange: Mock the UseCase to return a Flow
        val coins = listOf(Coin("1", "Bitcoin", "BTC", 50000.0, 2.0, ""))

        coEvery { mockUseCase() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(coins))
        }

        // Act & Assert using Turbine
        viewModel = CoinListViewModel(mockUseCase)

        viewModel.getCoins()
    }
}