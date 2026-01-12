package com.quickthought.cryptotrack.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerCoinItem(brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Fake Icon
        Box(modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(brush))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            // Fake Title
            Box(modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(20.dp)
                .background(brush))
            Spacer(modifier = Modifier.height(8.dp))
            // Fake Subtitle
            Box(modifier = Modifier
                .fillMaxWidth(0.2f)
                .height(15.dp)
                .background(brush))
        }
    }
}