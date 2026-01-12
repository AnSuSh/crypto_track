package com.quickthought.cryptotrack.presentation.coin_detail.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun CoinChart(
    prices: List<Double>,
    modifier: Modifier = Modifier,
    graphColor: Color = Green
) {
    Canvas(modifier = modifier) {
        val transparentGraphColor = graphColor.copy(alpha = 0.5f)

        // Find min and max to scale the graph to the screen size
        val upperValue = (prices.maxOrNull() ?: 0.0)
        val lowerValue = (prices.minOrNull() ?: 0.0)
        val range = upperValue - lowerValue

        val path = Path().apply {
            for (i in prices.indices) {
                val x = size.width * (i.toFloat() / (prices.size - 1))
                val y = size.height - (size.height * ((prices[i] - lowerValue) / range)).toFloat()

                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = graphColor,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )

        // Bonus: Draw a gradient fill under the line
        val fillPath = android.graphics.Path(path.asAndroidPath()).asComposePath().apply {
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(transparentGraphColor, Color.Transparent),
                endY = size.height
            )
        )
    }
}