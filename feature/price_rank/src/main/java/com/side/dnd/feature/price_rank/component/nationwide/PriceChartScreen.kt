package com.side.dnd.feature.price_rank.component.nationwide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.side.dnd.feature.price_rank.R
import com.side.dnd.feature.price_rank.model.NationWideUiState
import com.side.dnd.feature.price_rank.model.ProductChartData
import com.side.dnd.feature.price_rank.model.ProductRanking
import side.dnd.design.theme.EodigoColor
import side.dnd.design.theme.EodigoTheme

@Composable
fun PriceChartScreen(
    chartData : ProductChartData,
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
                style = EodigoTheme.typography.title1Medium,
                color = EodigoColor.Normal
            )
            Text(
                text = stringResource(id = R.string.price_chart_title),
                style = EodigoTheme.typography.body2Medium,
                color = EodigoColor.Gray500,
            )
        }
        
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text ="+${chartData.inflationRate}%",
                style = EodigoTheme.typography.title1Medium,
                color = EodigoColor.Normal
            )
            Text(
                text = stringResource(id = R.string.price_chart_content_first),
                style = EodigoTheme.typography.title1Medium,
                color = EodigoColor.Gray900
            )
        }

        Spacer(modifier = Modifier.size(108.dp))

        ChartContent()

    }
}

@Composable
fun ChartContent(){
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            lineSeries { series(13, 8, 7, 12, 0, 1, 15, 14, 0, 11, 6, 12, 0, 11, 12, 11) }
        }
    }
    CartesianChartHost(
        rememberCartesianChart(
            rememberLineCartesianLayer(),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom(),
        ),
        modelProducer,
        modifier = Modifier
            .padding(horizontal = 18.dp)
    )
}