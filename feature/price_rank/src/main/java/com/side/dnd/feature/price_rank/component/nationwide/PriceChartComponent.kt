package com.side.dnd.feature.price_rank.component.nationwide

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.side.dnd.feature.price_rank.model.AnnualPriceData
import side.dnd.design.theme.EodigoColor
import java.text.DecimalFormat
import kotlin.math.roundToInt

private fun formatPriceToManWon(price: Int): String {
    return when {
        price >= 10000 -> {
            val manWon = price / 10000
            val remainder = price % 10000
            when {
                remainder == 0 -> "${String.format("%,d", manWon)}만원"
                remainder >= 1000 -> "${String.format("%,d", manWon)}.${remainder / 1000}만원"
                else -> "${String.format("%,d", manWon)}만원"
            }
        }
        price >= 1000 -> "${String.format("%,d", price / 1000)}천원"
        else -> "${String.format("%,d", price)}원"
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun PriceChart(
    isEmptyKeyword: Boolean,
    data: List<AnnualPriceData>,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    val highlightColor = Color(0xFF8B5CF6)

    val annualPrices = if (isEmptyKeyword) data.map { it.averagePrice } else data.filter { it.year % 2 == 1 }.map { it.averagePrice }
    val years = if (isEmptyKeyword) listOf(2015, 2017, 2019, 2021, 2023, 2025) else data.map { it.year }.filter { it % 2 == 1 }
    val numPoints = years.size

    var selectedIndex by remember { mutableIntStateOf((numPoints - 1).coerceAtLeast(0)) }

    Box(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(
                    modifier = Modifier
                        .width(70.dp)
                        .height(250.dp)
                        .padding(top = 24.dp, bottom = 24.dp, start = 16.dp)
                ) {
                    val yLabels = if (isEmptyKeyword) {
                        listOf("n만원", "n백원", "n십원", "n원")
                    } else {
                        val minPrice = data.map { it.averagePrice }.minOrNull() ?: 0
                        val maxPrice = data.map { it.averagePrice }.maxOrNull() ?: 0
                        val priceRange = (maxPrice - minPrice).coerceAtLeast(1)
                        val yAxisRange = priceRange * 1.5f
                        val yAxisStart = minPrice - (yAxisRange - priceRange) / 2

                        val yPositions = listOf(
                            (yAxisStart + yAxisRange * 0.95).toInt(),
                            (yAxisStart + yAxisRange * 0.7).toInt(),
                            (yAxisStart + yAxisRange * 0.4).toInt(),
                            (yAxisStart + yAxisRange * 0.1).toInt()
                        )
                        yPositions.map { formatPriceToManWon(it) }
                    }

                    val yGridPositions = listOf(
                        size.height * 0.05f,
                        size.height * 0.3f,
                        size.height * 0.6f,
                        size.height * 0.9f
                    )

                    yLabels.forEachIndexed { index, label ->
                        val yPosition = yGridPositions[index]
                        val textLayoutResult = textMeasurer.measure(
                            text = label,
                            style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                        )
                        drawText(
                            textMeasurer = textMeasurer,
                            text = label,
                            style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                            topLeft = Offset(
                                x = 0f,
                                y = yPosition - textLayoutResult.size.height / 2
                            )
                        )
                    }
                }

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(top = 24.dp, bottom = 24.dp, end = 16.dp)
                        .pointerInput(Unit) {
                            detectDragGestures { change, _ ->
                                val xPos = change.position.x
                                val xStep =
                                    if (numPoints > 1) size.width / (numPoints - 1) else size.width
                                val closestIndex =
                                    (xPos / xStep).roundToInt().coerceIn(0, numPoints - 1)
                                selectedIndex = closestIndex
                            }
                        }
                ) {
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

                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    yPositions.forEach { price ->
                        val y = size.height - (price - yAxisStart) * yFactor
                        drawLine(
                            color = EodigoColor.Gray300,
                            start = Offset(x = 0f, y = y),
                            end = Offset(x = size.width, y = y),
                            pathEffect = pathEffect
                        )
                    }

                    years.forEachIndexed { index, _ ->
                        val x = index * xStep
                        drawLine(
                            color = EodigoColor.Gray300,
                            start = Offset(x = x, y = 0f),
                            end = Offset(x = x, y = size.height),
                            pathEffect = pathEffect
                        )
                    }

                    for (i in 0 until numPoints - 1) {
                        val startX = i * xStep
                        val startY = size.height - (annualPrices[i] - yAxisStart) * yFactor
                        val endX = (i + 1) * xStep
                        val endY = size.height - (annualPrices[i + 1] - yAxisStart) * yFactor

                        drawLine(
                            color = EodigoColor.Normal,
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    val selectedX = selectedIndex * xStep
                    val selectedY =
                        size.height - (annualPrices[selectedIndex] - yAxisStart) * yFactor

                    drawCircle(
                        color = highlightColor.copy(alpha = 0.2f),
                        radius = 20.dp.toPx(),
                        center = Offset(selectedX, selectedY)
                    )
                    drawCircle(
                        color = highlightColor.copy(alpha = 0.8f),
                        radius = 8.dp.toPx(),
                        center = Offset(selectedX, selectedY)
                    )

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 86.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                years.forEach { year ->
                    Text(
                        text = year.toString(),
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                    )
                }
            }
        }


        with(density) {
            val chartStartX = 70.dp
            val chartEndPadding = 16.dp
            val screenWidth = 320.dp
            val chartWidth = screenWidth - chartStartX - chartEndPadding
            val xStep = if (numPoints > 1) chartWidth / (numPoints - 1) else chartWidth
            val selectedX = chartStartX + selectedIndex * xStep
            
            val minPrice = annualPrices.minOrNull() ?: 0
            val maxPrice = annualPrices.maxOrNull() ?: 0
            val priceRange = (maxPrice - minPrice).coerceAtLeast(1)
            val yAxisRange = priceRange * 1.5f
            val yAxisStart = minPrice - (yAxisRange - priceRange) / 2
            val chartHeight = 250.dp - 48.dp
            val yFactor = if (yAxisRange > 0) chartHeight.toPx() / yAxisRange else 1f
            val selectedYFromTop = (annualPrices[selectedIndex] - yAxisStart) * yFactor
            val circleY = 24.dp.toPx() + (chartHeight.toPx() - selectedYFromTop)
            val circleRadius = 20.dp
            val cardHeight = 32.dp
            val arrowHeight = 8.dp
            val tooltipY = (circleY / density.density).dp - circleRadius - cardHeight - arrowHeight


            Card(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = (selectedX - 40.dp).roundToPx().coerceAtLeast(0),
                            y = tooltipY.roundToPx()
                        )
                    }
                    .drawBehind {
                        val arrowWidth = 12.dp.toPx()
                        val arrowHeight = 8.dp.toPx()
                        val arrowOffsetX = size.width * 0.5f

                        val arrowPath = Path().apply {
                            moveTo(arrowOffsetX - arrowWidth / 2, size.height)
                            quadraticBezierTo(
                                arrowOffsetX - arrowWidth / 4, size.height + arrowHeight / 2,
                                arrowOffsetX, size.height + arrowHeight
                            )
                            quadraticBezierTo(
                                arrowOffsetX + arrowWidth / 4, size.height + arrowHeight / 2,
                                arrowOffsetX + arrowWidth / 2, size.height
                            )
                            close()
                        }
                        drawPath(arrowPath, Color.Black)
                    },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Text(
                    text = if (isEmptyKeyword) "n,nnn원" else DecimalFormat("#,###").format(annualPrices[selectedIndex]),
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
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
        isEmptyKeyword = true,
        data = sampleData,
        modifier = Modifier.padding(16.dp)
    )
}