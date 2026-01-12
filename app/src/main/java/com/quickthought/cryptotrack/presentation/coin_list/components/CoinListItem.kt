package com.quickthought.cryptotrack.presentation.coin_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.quickthought.cryptotrack.domain.model.Coin
import java.util.Locale

@Composable
fun CoinListItem(
    coin: Coin,
    onItemClick: (Coin) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(coin) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = coin.iconUrl,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(text = coin.name, style = MaterialTheme.typography.titleMedium)
            Text(text = coin.symbol.uppercase(), style = MaterialTheme.typography.bodySmall)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "$${String.format(Locale.getDefault(), "%.2f", coin.priceUsd)}")
            Text(
                text = "${String.format(Locale.getDefault(), "%.2f", coin.priceChangePercent24h)}%",
                color = if (coin.priceChangePercent24h >= 0) Color.Green else Color.Red,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}