package com.quickthought.cryptotrack.presentation.coin_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.quickthought.cryptotrack.domain.model.Coin
import com.quickthought.cryptotrack.presentation.coin_detail.components.CoinChart
import com.quickthought.cryptotrack.presentation.coin_detail.components.CoinDetailHeader

@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        state.coinDetail?.let { data ->
            // 1. Header Section
            CoinDetailHeader(coin = data.coin)

            // 2. Chart Section
            CoinChart(
                prices = data.chartData.yValues.map { it.toDouble() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp)
            )

            // 3. Stats Section (Market Cap, Rank, etc.)
            CoinStats(coin = data.coin)
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CoinStats(coin: Coin) {
    Column(modifier = Modifier.padding(16.dp)) {
        StatRow("Market Cap", "$${coin.marketCap}")
        StatRow("24h High", "$${coin.high24h}")
        StatRow("24h Low", "$${coin.low24h}")
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}