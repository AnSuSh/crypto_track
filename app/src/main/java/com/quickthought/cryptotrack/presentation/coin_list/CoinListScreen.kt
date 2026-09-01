package com.quickthought.cryptotrack.presentation.coin_list

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.quickthought.cryptotrack.presentation.Screen
import com.quickthought.cryptotrack.presentation.coin_list.components.CoinListItem
import com.quickthought.cryptotrack.presentation.coin_list.components.ShimmerCoinItem
import com.quickthought.cryptotrack.presentation.components.BannerAdView
import com.quickthought.cryptotrack.presentation.util.shimmerBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val brush = shimmerBrush()

    // Search UI state
    var showSearch by remember { mutableStateOf(false) }
    val query = viewModel.searchQuery.value

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            // Top app bar with search icon
            TopAppBar(
                title = {
                    if (showSearch) {
                        OutlinedTextField(
                            value = query,
                            onValueChange = { viewModel.updateSearchQuery(it) },
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .animateContentSize(),
                            singleLine = true,
                            placeholder = { Text(text = "Search") },
                        )
                    } else {
                        Text(text = "Coins", modifier = Modifier.animateContentSize())
                    }
                },
                actions = {
                    IconButton(onClick = {
                        showSearch = !showSearch
                        if (!showSearch) {
                            if (query.isBlank()) return@IconButton
                            viewModel.updateSearchQuery("") // Clear search when closing
                        }
                    }) {
                        // Apply Animation during icon change
                        if (showSearch) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = "Cancel Search",
                                modifier = Modifier.animateContentSize(),
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                modifier = Modifier.animateContentSize(),
                            )
                        }
                    }
                },
            )

            // Content list
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(top = 56.dp), // leave space for the top bar
            ) {
                if (state.isLoading) {
                    // Show 10 shimmer items while loading
                    items(10) {
                        ShimmerCoinItem(brush = brush)
                    }
                } else {
                    val coins = state.coins
                    if (coins.isEmpty()) {
                        item {
                            Text(
                                text = "No coins found.",
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                textAlign = TextAlign.Center,
                            )
                        }
                    } else {
                        items(coins) { coin ->
                            CoinListItem(
                                coin = coin,
                                onItemClick = {
                                    navController.navigate(Screen.CoinDetailScreen.route + "/${coin.id}")
                                },
                                onFavoriteClick = {
                                    viewModel.toggleFavorite(coin.id)
                                },
                            )
                        }
                    }
                }
            }

            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center),
                )
            }
        }
        BannerAdView(modifier = Modifier.fillMaxWidth())
    }
}
