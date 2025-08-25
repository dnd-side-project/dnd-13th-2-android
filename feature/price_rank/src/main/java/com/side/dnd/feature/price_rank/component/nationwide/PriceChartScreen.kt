package com.side.dnd.feature.price_rank.component.nationwide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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

        ChartContent(chartData = chartData)

    }
}

@Composable
fun ChartContent(
    chartData: ProductChartData = MockProductChartData.sampleChartData
) {

}
