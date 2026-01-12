package com.quickthought.cryptotrack.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quickthought.cryptotrack.core.Resource
import com.quickthought.cryptotrack.domain.use_case.get_coin_detail.GetCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinChartUseCase: GetCoinDetailUseCase,
    savedStateHandle: SavedStateHandle // To get the 'coinId' from NavHost
) : ViewModel() {

    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
        // Automatically extract the coinId passed during navigation
        savedStateHandle.get<String>("coinId")?.let { id ->
            loadCoinDetail(id)
        }
    }

    private fun loadCoinDetail(id: String) {
        getCoinChartUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        coinDetail = result.data,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Error",
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}