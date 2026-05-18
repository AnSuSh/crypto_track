package com.quickthought.cryptotrack.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quickthought.cryptotrack.core.Resource
import com.quickthought.cryptotrack.domain.model.Coin
import com.quickthought.cryptotrack.domain.use_case.favorites.AddFavoriteUseCase
import com.quickthought.cryptotrack.domain.use_case.favorites.GetFavoriteIdsUseCase
import com.quickthought.cryptotrack.domain.use_case.favorites.RemoveFavoriteUseCase
import com.quickthought.cryptotrack.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel
    @Inject
    constructor(
        private val getCoinsUseCase: GetCoinsUseCase,
        private val getFavoriteIdsUseCase: GetFavoriteIdsUseCase,
        private val addFavoriteUseCase: AddFavoriteUseCase,
        private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    ) : ViewModel() {
        // Internal state that we modify
        private val _state = mutableStateOf(CoinListState())

        // Public state that the UI observes (read-only)
        val state: State<CoinListState> = _state

        // Search query maintained in the ViewModel so it survives recompositions
        private val _searchQuery = mutableStateOf("")
        val searchQuery: State<String> = _searchQuery

        init {
            getCoins()
        }

        fun getCoins() {
            val coinsFlow = getCoinsUseCase()
            val favoriteIdsFlow = getFavoriteIdsUseCase()

            coinsFlow
                .combine(favoriteIdsFlow) { result, favoriteIds ->
                    when (result) {
                        is Resource.Success -> {
                            val coins = result.data ?: emptyList()
                            // Apply favorite flags and reorder
                            val withFavorites =
                                coins
                                    .map { coin ->
                                        coin.copy(isFavorite = favoriteIds.contains(coin.id))
                                    }.sortedWith(compareByDescending<Coin> { it.isFavorite }.thenBy { it.name })

                            _state.value = CoinListState(coins = withFavorites)
                        }

                        is Resource.Error -> {
                            _state.value =
                                CoinListState(
                                    error = result.message ?: "An unexpected error occurred",
                                )
                        }

                        is Resource.Loading -> {
                            _state.value = CoinListState(isLoading = true)
                        }
                    }
                }.launchIn(viewModelScope)
        }

        fun toggleFavorite(coinId: String) {
            viewModelScope.launch {
                // Check the current state to decide whether to add or remove
                val isCurrentlyFavorite =
                    _state.value.coins
                        .find { it.id == coinId }
                        ?.isFavorite == true

                if (isCurrentlyFavorite) {
                    removeFavoriteUseCase(coinId)
                } else {
                    addFavoriteUseCase(coinId)
                }

                // No need to update the state here, as the combined flow will handle it
            }
        }

        // Update the search query and apply local filter to the already-loaded coins
        fun updateSearchQuery(query: String) {
            if (query.isBlank()) {
                _searchQuery.value = ""
                getCoins()
                return
            }
            _searchQuery.value = query
            applyLocalFilter()
        }

        private fun applyLocalFilter() {
            val current = _state.value
            val query = _searchQuery.value.trim().lowercase()

            if (query.isEmpty()) {
                // Nothing to filter; keep coins as-is (they're already favorite-first ordered)
                _state.value = current.copy()
                return
            }

            val filtered =
                current.coins.filter { coin ->
                    coin.name.lowercase().contains(query) || coin.symbol.lowercase().contains(query)
                }

            // Ensure favorites still come first in the filtered list
            val reordered =
                filtered.sortedWith(
                    compareByDescending<Coin> {
                        it.isFavorite
                    }.thenBy { it.name },
                )

            _state.value = current.copy(coins = reordered)
        }
    }
