package com.quickthought.cryptotrack.presentation.coin_detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.quickthought.cryptotrack.domain.model.Coin
import java.util.Locale

@Composable
fun CoinDetailHeader(
    coin: Coin,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Define references for the elements
        val (icon, name, symbol, price, change, rank) = createRefs()

        AsyncImage(
            model = coin.iconUrl,
            contentDescription = coin.name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = coin.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(icon.top)
                start.linkTo(icon.end, margin = 16.dp)
            }
        )

        Text(
            text = coin.symbol.uppercase(),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.constrainAs(symbol) {
                top.linkTo(name.bottom)
                start.linkTo(name.start)
            }
        )

        // The "Price" section on the right side
        Text(
            text = "$${String.format(Locale.getDefault(), "%,.2f", coin.priceUsd)}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(price) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        )

        // Price Change with background pill/chip look
        Surface(
            color = if (coin.priceChangePercent24h >= 0) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.constrainAs(change) {
                top.linkTo(price.bottom, margin = 4.dp)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                text = "${if (coin.priceChangePercent24h >= 0) "+" else ""}${
                    String.format(
                        Locale.getDefault(),
                        "%.2f",
                        coin.priceChangePercent24h
                    )
                }%",
                color = if (coin.priceChangePercent24h >= 0) Color(0xFF2E7D32) else Color(0xFFC62828),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}