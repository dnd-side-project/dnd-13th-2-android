package com.side.dnd.feature.price_rank.component.nationwide

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.side.dnd.feature.price_rank.R
import com.side.dnd.feature.price_rank.model.MockProductRanking
import com.side.dnd.feature.price_rank.model.ProductRanking
import side.dnd.design.theme.EodigoColor
import side.dnd.design.theme.EodigoTheme

@Composable
fun PriceMapScreen(
    productRanking: ProductRanking,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(EodigoColor.White)
    ) {
        Spacer(modifier = Modifier.size(52.dp))

        Text(
            text = stringResource(id = R.string.price_map_title),
            style = EodigoTheme.typography.body2Medium,
            color = EodigoColor.Gray500,
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.price_map_content_first),
                style = EodigoTheme.typography.title1Medium,
                color = EodigoColor.Normal
            )
            Text(
                text = stringResource(id = R.string.price_map_content_second),
                style = EodigoTheme.typography.title1Medium,
                color = EodigoColor.Gray900
            )
        }
        Spacer(modifier = Modifier.height(52.dp))
        MapScreen(productRanking = productRanking)
    }
}

@Composable
fun MapScreen(
    productRanking: com.side.dnd.feature.price_rank.model.ProductRanking?
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val maxWidth = maxWidth
        val maxHeight = 400.dp

        Image(
            painter = painterResource(id = R.drawable.ic_nationwide_map),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeight)
        )

        if (productRanking != null) {
            val rankings = productRanking.ranking.take(5)

            rankings.forEachIndexed { index, regionRanking ->
                val isFirstRank = index == 0
                val priceText = "${regionRanking.price}원"

                val (xOffset, yOffset) = when (index) {
                    0 -> Pair(0.5f, 0.2f) // 서울 - 상단 중앙
                    1 -> Pair(0.7f, 0.7f) // 부산 - 우하단
                    2 -> Pair(0.6f, 0.5f) // 대구 - 중앙
                    3 -> Pair(0.3f, 0.3f) // 인천 - 좌상단
                    4 -> Pair(0.4f, 0.8f) // 광주 - 좌하단
                    else -> Pair(0.5f, 0.5f)
                }

                PriceCircle(
                    text = priceText,
                    isFirstRank = isFirstRank,
                    modifier = Modifier
                        .offset(
                            x = maxWidth * xOffset - (if (isFirstRank) 61.dp else 40.dp),
                            y = maxHeight * yOffset - (if (isFirstRank) 61.dp else 40.dp)
                        )
                )
            }
        } else {
            Text(
                text = "지도 데이터를 불러오는 중...",
                style = EodigoTheme.typography.body2Medium,
                color = EodigoColor.Gray500,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun PriceCircle(
    text: String,
    isFirstRank: Boolean = false,
    modifier: Modifier = Modifier
) {
    val size = if (isFirstRank) 122.dp else 80.dp
    val bgResource = if (isFirstRank) painterResource(R.drawable.ic_first_price_bg) else painterResource(R.drawable.ic_second_price_bg)
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .clickable{}
    ) {
        Image(
            painter = bgResource,
            contentDescription = null,
        )
        Text(
            text = text,
            color = Color.White,
            style = EodigoTheme.typography.body3Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PriceMapScreenPreview() {
    PriceMapScreen(
        productRanking = com.side.dnd.feature.price_rank.model.MockProductRanking.sampleProductRanking
    )
}

@Preview(showBackground = true)
@Composable
fun PriceMapScreenLoadingPreview() {
    PriceMapScreen(
        productRanking = MockProductRanking.sampleProductRanking
    )
}

