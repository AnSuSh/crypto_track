package com.quickthought.cryptotrack.presentation.coin_list

import com.quickthought.cryptotrack.core.Resource
import com.quickthought.cryptotrack.domain.model.Coin
import com.quickthought.cryptotrack.domain.use_case.favorites.AddFavoriteUseCase
import com.quickthought.cryptotrack.domain.use_case.favorites.GetFavoriteIdsUseCase
import com.quickthought.cryptotrack.domain.use_case.favorites.RemoveFavoriteUseCase
import com.quickthought.cryptotrack.domain.use_case.get_coins.GetCoinsUseCase
import com.quickthought.cryptotrack.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
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
    private val addFavUseCase: AddFavoriteUseCase = mockk(relaxed = true)
    private val removeFavUseCase: RemoveFavoriteUseCase = mockk(relaxed = true)

    @Test
    fun `ViewModel should emit loading then success state and handle favorites toggle and search`() = runTest {
        // Arrange: Mock the UseCase to return a Flow
        val coin1 = Coin("1", "Bitcoin", "BTC", 50000.0, 2.0, "img1")
        val coin2 = Coin("2", "Ethereum", "ETH", 4000.0, -1.0, "img2")
        val coin3 = Coin("3", "BitTorrent", "BTT", 0.01, 0.0, "img3")
        val coins = listOf(coin1, coin2, coin3)

        coEvery { mockUseCase() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(coins))
        }

        coEvery { favIdsUseCase() } returns flow { emit(emptyList<String>()) }

        // Act
        viewModel = CoinListViewModel(mockUseCase, favIdsUseCase, addFavUseCase, removeFavUseCase)

        // After fetching, coins should be set (no favorites yet)
        val stateAfterFetch = viewModel.state.value
        assertEquals(3, stateAfterFetch.coins.size)
        assertEquals(false, stateAfterFetch.coins[0].isFavorite)

        // Toggle favorite for coin2
        viewModel.toggleFavorite("2")

        // Verify the appropriate use case was invoked
        coVerify { addFavUseCase("2") }

        // Simulate favorite list update: when favoriteIds flow emits, the ViewModel's combined flow will update.
        // For unit test simplicity we assert that toggleFavorite triggered the use case above.

        // Test search: search for "bit" should return Bitcoin and BitTorrent, with favorites first
        viewModel.updateSearchQuery("bit")
        val stateAfterSearch = viewModel.state.value
        // Should contain 2 entries: Bitcoin and BitTorrent
        assertEquals(2, stateAfterSearch.coins.size)
        assertEquals(true, stateAfterSearch.coins[0].name.lowercase().contains("bit"))
    }
}