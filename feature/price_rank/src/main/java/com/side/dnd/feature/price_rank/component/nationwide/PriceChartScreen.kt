package com.side.dnd.feature.price_rank.component.nationwide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.side.dnd.feature.price_rank.R
import com.side.dnd.feature.price_rank.model.MockProductChartData
import com.side.dnd.feature.price_rank.model.ProductChartData
import side.dnd.design.theme.EodigoColor
import side.dnd.design.theme.EodigoTheme

@Composable
fun PriceChartScreen(
    chartData: ProductChartData,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(EodigoColor.White)
    ) {
        Spacer(modifier = Modifier.size(52.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "\"${chartData.productName}\"",
                style = EodigoTheme.typography.body3Medium,
                color = EodigoColor.Black
            )
            Text(
                text = stringResource(id = R.string.price_chart_title),
                style = EodigoTheme.typography.body3Medium,
                color = EodigoColor.Gray900,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val formattedRate = String.format("%.3f", chartData.inflationRate)

            Text(
                text = "+${formattedRate}% ",
                style = EodigoTheme.typography.title1Medium,
                color = EodigoColor.Normal,

            )
            Text(
                text = stringResource(id = R.string.price_chart_content_first),
                style = EodigoTheme.typography.title1Medium,
                color = EodigoColor.Gray900
            )
        }

        Spacer(modifier = Modifier.size(40.dp))
        PriceChart(
            data = MockProductChartData.sampleChartData.annualData,
            modifier = Modifier.padding(16.dp)
        )
//        ChartContent(chartData = chartData)

    }
}
//
//@Composable
//fun ChartContent(
//    chartData: ProductChartData
//) {
//    val chartEntryModel = remember(chartData) {
//        entryModelOf(*chartData.annualData.mapIndexed { index, price ->
//            index.toFloat() to price.toFloat()
//        }.toTypedArray())
//    }
//
//    val currentYear = 2024
//    val years = (currentYear - 9..currentYear).map { it.toString() }
//
//    val bottomAxisValueFormatter = remember {
//        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
//            val index = value.toInt()
//            if (index < years.size) {
//                years[index]
//            } else ""
//        }
//    }
//
//    val startAxisValueFormatter = remember {
//        AxisValueFormatter<AxisPosition.Vertical.Start> { value, _ ->
//            when {
//                value >= 10000 -> "${(value / 10000).toInt()}만원"
//                value >= 1000 -> "${(value / 1000).toInt()}천원"
//                value >= 100 -> "${(value / 100).toInt()}백원"
//                value >= 10 -> "${(value / 10).toInt()}십원"
//                else -> "${value.toInt()}원"
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//    ) {
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp)
//                .background(EodigoColor.Light.copy(alpha = 0.1f))
//        ) {
//            Chart(
//                chart = lineChart(),
//                model = chartEntryModel,
//                startAxis = startAxis(
//                    valueFormatter = startAxisValueFormatter,
//                    guideline = null,
//                    tick = null,
//                    axis = null
//                ),
//                bottomAxis = bottomAxis(
//                    valueFormatter = bottomAxisValueFormatter,
//                    guideline = null,
//                    tick = null,
//                    axis = null
//                ),
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            )
//        }
//
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PriceChartScreenPreview() {
//    PriceChartScreen(
//        chartData = ProductChartData(
//            productId = 1,
//            productName = "쌀",
//            inflationRate = 15.3,
//            priceHistory = listOf(2500, 2800, 3200, 3500, 3800, 4200, 4500, 4800, 5200, 5500)
//        )
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ChartContentPreview() {
//    ChartContent(
//        chartData = ProductChartData(
//            productId = 1,
//            productName = "쌀",
//            inflationRate = 15.3,
//            priceHistory = listOf(2500, 2800, 3200, 3500, 3800, 4200, 4500, 4800, 5200, 5500)
//        )
//    )
//}
