package com.quickthought.cryptotrack.presentation.coin_list

import com.quickthought.cryptotrack.core.Resource
import com.quickthought.cryptotrack.domain.model.Coin
import com.quickthought.cryptotrack.domain.use_case.favorites.AddFavoriteUseCase
import com.quickthought.cryptotrack.domain.use_case.favorites.GetFavoriteIdsUseCase
import com.quickthought.cryptotrack.domain.use_case.favorites.RemoveFavoriteUseCase
import com.quickthought.cryptotrack.domain.use_case.get_coins.GetCoinsUseCase
import com.quickthought.cryptotrack.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CoinListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CoinListViewModel
    private val mockUseCase: GetCoinsUseCase = mockk()
    private val favIdsUseCase: GetFavoriteIdsUseCase = mockk()
    private val addFavUseCase: AddFavoriteUseCase = mockk()
    private val removeFavUseCase: RemoveFavoriteUseCase = mockk()

    @Test
    fun `ViewModel should emit loading then success state and handle favorites toggle`() = runTest {
        // Arrange: Mock the UseCase to return a Flow
        val coin1 = Coin("1", "Bitcoin", "BTC", 50000.0, 2.0, "img1")
        val coin2 = Coin("2", "Ethereum", "ETH", 4000.0, -1.0, "img2")
        val coins = listOf(coin1, coin2)

        coEvery { mockUseCase() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(coins))
        }

        // Act
        viewModel = CoinListViewModel(mockUseCase, favIdsUseCase, addFavUseCase, removeFavUseCase)
        // initial fetch done in init

        // After fetching, coins should be set (no favorites yet)
        val stateAfterFetch = viewModel.state.value
        assertEquals(2, stateAfterFetch.coins.size)
        assertEquals(false, stateAfterFetch.coins[0].isFavorite)

        // Toggle favorite for coin2
        viewModel.toggleFavorite("2")

        // Now coin2 should be marked favorite and appear first
        val stateAfterToggle = viewModel.state.value
        assertEquals(true, stateAfterToggle.coins[0].isFavorite)
        assertEquals("2", stateAfterToggle.coins[0].id)
    }
}