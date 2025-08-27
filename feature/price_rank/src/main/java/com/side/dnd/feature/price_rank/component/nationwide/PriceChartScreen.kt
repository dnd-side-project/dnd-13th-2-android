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
//    val isEmptyData = chartData.annualData.isEmpty()
    val isEmptyData = true
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
            if (isEmptyData) {
                Text(
                    text = stringResource(R.string.price_chart_empty_title),
                    style = EodigoTheme.typography.body3Medium,
                    color = EodigoColor.Gray500
                )
            }else {
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
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val formattedRate = String.format("%.3f", chartData.inflationRate)
            val firstText = if (isEmptyData) stringResource(R.string.price_chart_content_empty_first) else "+${formattedRate}% "
            val secondText = if (isEmptyData) stringResource(id = R.string.price_chart_content_empty_second) else stringResource(id = R.string.price_chart_content_first)

            Text(
                text = firstText,
                style = EodigoTheme.typography.title1Medium,
                color = EodigoColor.Normal,

            )
            Text(
                text = secondText,
                style = EodigoTheme.typography.title1Medium,
                color = EodigoColor.Gray900
            )
        }

        Spacer(modifier = Modifier.size(40.dp))

        PriceChart(
            data = MockProductChartData.sampleChartData.annualData,
            modifier = Modifier
        )
    }
}
