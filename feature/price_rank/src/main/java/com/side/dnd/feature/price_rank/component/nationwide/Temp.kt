package com.side.dnd.feature.price_rank.component.nationwide

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.side.dnd.feature.price_rank.model.AnnualPriceData


@OptIn(ExperimentalTextApi::class)
@Composable
fun PriceChart(
    data: List<AnnualPriceData>,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val axisColor = Color(0xFFF2F2F2)
    val lineColor = Color(0xFF8B5CF6)
    val highlightColor = Color(0xFF8B5CF6)

    if (data.isEmpty()) {
        Text("데이터가 없습니다.", modifier = modifier)
        return
    }
    
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .width(60.dp)
                .height(250.dp)
                .padding(top = 24.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.End
        ) {
            val yLabels = listOf("n만원", "n백원", "n십원", "n원")
            yLabels.forEach { label ->
                Text(
                    text = label,
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(start = 0.dp, end = 24.dp, top = 24.dp, bottom = 24.dp)
        ) {
            if (data.isEmpty() || size.width <= 0 || size.height <= 0 || size.width.isNaN() || size.height.isNaN()) return@Canvas

            val annualPrices = data.map { it.averagePrice }
            val years = data.map { it.year }
            val numPoints = data.size

            if (numPoints <= 1) return@Canvas

            val minPrice = annualPrices.minOrNull() ?: 0
            val maxPrice = annualPrices.maxOrNull() ?: 0
            val priceRange = (maxPrice - minPrice).coerceAtLeast(1)
            val yAxisRange = priceRange * 1.5f
            val yAxisStart = minPrice - (yAxisRange - priceRange) / 2

            val xStep = if (numPoints > 1) size.width / (numPoints - 1) else size.width
            val yFactor = if (yAxisRange > 0) size.height / yAxisRange else 1f

            val yPositions = listOf(
                (yAxisStart + yAxisRange * 0.1).toFloat(),
                (yAxisStart + yAxisRange * 0.4).toFloat(),
                (yAxisStart + yAxisRange * 0.7).toFloat(),
                (yAxisStart + yAxisRange * 0.95).toFloat()
            )

            yPositions.forEach { price ->
                val y = size.height - (price - yAxisStart) * yFactor
                drawLine(
                    color = axisColor,
                    start = Offset(x = 0f, y = y),
                    end = Offset(x = size.width, y = y)
                )
            }

            years.forEachIndexed { index, year ->
                val x = index * xStep
                val textOffset = 20.dp.toPx()
                val safeX = (x - textOffset).coerceAtLeast(0f).coerceAtMost(size.width - textOffset)
                val safeY = (size.height + 10.dp.toPx()).coerceAtLeast(0f)
                
                if (safeX >= 0 && safeY >= 0 && safeX < size.width && safeY < size.height) {
                    drawText(
                        textMeasurer = textMeasurer,
                        text = year.toString(),
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                        topLeft = Offset(x = safeX, y = safeY)
                    )
                }
            }

            for (i in 0 until numPoints - 1) {
                val startX = i * xStep
                val startY = size.height - (annualPrices[i] - yAxisStart) * yFactor
                val endX = (i + 1) * xStep
                val endY = size.height - (annualPrices[i + 1] - yAxisStart) * yFactor

                drawLine(
                    color = lineColor,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            val lastIndex = numPoints - 1
            val lastX = lastIndex * xStep
            val lastY = size.height - (annualPrices[lastIndex] - yAxisStart) * yFactor

            drawCircle(color = highlightColor.copy(alpha = 0.2f), radius = 20.dp.toPx(), center = Offset(lastX, lastY))
            drawCircle(color = highlightColor.copy(alpha = 0.8f), radius = 8.dp.toPx(), center = Offset(lastX, lastY))

            val labelText = "${annualPrices[lastIndex]}원"
            val labelWidth = 80.dp.toPx()
            val labelHeight = 35.dp.toPx()
            val labelOffset = 20.dp.toPx()

            val minX = (labelWidth / 2).coerceAtLeast(0f)
            val maxX = (size.width - labelWidth / 2).coerceAtLeast(minX + 1f)
            val safeX = lastX.coerceIn(minX, maxX)
            val safeY = (lastY - labelHeight - labelOffset).coerceAtLeast(0f)

            drawIntoCanvas {
                it.nativeCanvas.drawRoundRect(
                    safeX - labelWidth / 2,
                    safeY,
                    safeX + labelWidth / 2,
                    safeY + labelHeight,
                    15.dp.toPx(),
                    15.dp.toPx(),
                    androidx.compose.ui.graphics.Paint().apply { color = Color.Black }.asFrameworkPaint()
                )
            }

            val textX = (safeX - labelWidth / 2 + 10.dp.toPx()).coerceAtLeast(0f).coerceAtMost(size.width - 10.dp.toPx())
            val textY = (safeY + 10.dp.toPx()).coerceAtLeast(0f)
            
            if (textX >= 0 && textY >= 0 && textX < size.width && textY < size.height) {
                drawText(
                    textMeasurer = textMeasurer,
                    text = labelText,
                    style = TextStyle(color = Color.White, fontSize = 12.sp),
                    topLeft = Offset(textX, textY)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PriceChartPreview() {
    val sampleData = listOf(
        AnnualPriceData(2015, 3500),
        AnnualPriceData(2016, 3100),
        AnnualPriceData(2017, 3200),
        AnnualPriceData(2018, 2900),
        AnnualPriceData(2019, 2700),
        AnnualPriceData(2020, 2650),
        AnnualPriceData(2021, 3300),
        AnnualPriceData(2022, 3250),
        AnnualPriceData(2023, 4000),
        AnnualPriceData(2024, 4500),
        AnnualPriceData(2025, 4800)
    )

    PriceChart(
        data = sampleData,
        modifier = Modifier.padding(16.dp)
    )
}