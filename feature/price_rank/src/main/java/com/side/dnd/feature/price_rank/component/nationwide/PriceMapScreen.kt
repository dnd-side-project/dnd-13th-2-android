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
import com.side.dnd.feature.price_rank.model.RegionRanking
import side.dnd.design.theme.EodigoColor
import side.dnd.design.theme.EodigoTheme

@Composable
fun PriceMapScreen(
    productRanking: ProductRanking,
    isEmptyKeyword: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(EodigoColor.White)
    ) {
        Spacer(modifier = Modifier.size(52.dp))

        if (isEmptyKeyword) {
            Text(
                text = stringResource(id = R.string.price_map_empty_title),
                style = EodigoTheme.typography.body2Medium,
                color = EodigoColor.Gray500,
            )
            Spacer(modifier = Modifier.size(5.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.price_map_empty_content_first),
                    style = EodigoTheme.typography.title1Medium,
                    color = EodigoColor.Normal
                )
                Text(
                    text = stringResource(id = R.string.price_map_empty_content_second),
                    style = EodigoTheme.typography.title1Medium,
                    color = EodigoColor.Gray900
                )
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.price_map_title_first),
                    style = EodigoTheme.typography.body3Medium,
                    color = EodigoColor.Gray500
                )
                Text(
                    text = " \"${productRanking.productName}\" ",
                    style = EodigoTheme.typography.body3Medium,
                    color = EodigoColor.Gray900
                )
                Text(
                    text = stringResource(id = R.string.price_map_title_second),
                    style = EodigoTheme.typography.body3Medium,
                    color = EodigoColor.Gray500
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = productRanking.ranking.first().regionName,
                style = EodigoTheme.typography.title1Medium,
                color = EodigoColor.Normal,
            )
        }
        Spacer(modifier = Modifier.height(52.dp))
        MapScreen(
            isEmptyKeyword = isEmptyKeyword,
            productRanking = productRanking
        )
    }
}

@Composable
fun MapScreen(
    isEmptyKeyword: Boolean = false,
    productRanking: ProductRanking
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

        if (!isEmptyKeyword && productRanking.ranking.isNotEmpty()) {
            val rankings = productRanking.ranking.take(3)

            rankings.forEachIndexed { index, regionRanking ->
                val isFirstRank = index == 0

                val (xOffset, yOffset) = when (regionRanking.regionName) {
                    "서울" -> Pair(0.5f, 0.2f)
                    "부산" -> Pair(0.7f, 0.7f)
                    "대구" -> Pair(0.6f, 0.5f)
                    "인천" -> Pair(0.3f, 0.3f)
                    "광주" -> Pair(0.4f, 0.8f)
                    "대전" -> Pair(0.45f, 0.6f)
                    "울산" -> Pair(0.75f, 0.6f)
                    "세종" -> Pair(0.4f, 0.5f)
                    "경기" -> Pair(0.4f, 0.25f)
                    "강원" -> Pair(0.6f, 0.15f)
                    "충북" -> Pair(0.5f, 0.4f)
                    "충남" -> Pair(0.35f, 0.55f)
                    "전북" -> Pair(0.35f, 0.7f)
                    "전남" -> Pair(0.35f, 0.85f)
                    "경북" -> Pair(0.65f, 0.4f)
                    "경남" -> Pair(0.65f, 0.75f)
                    "제주" -> Pair(0.3f, 0.95f)
                    else -> Pair(0.5f, 0.5f)
                }

                PriceCircle(
                    text = "${regionRanking.price}원",
                    isFirstRank = isFirstRank,
                    modifier = Modifier
                        .offset(
                            x = maxWidth * xOffset - (if (isFirstRank) 61.dp else 40.dp),
                            y = maxHeight * yOffset - (if (isFirstRank) 61.dp else 40.dp)
                        )
                )
            }
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
    val bgResource =
        if (isFirstRank) painterResource(R.drawable.ic_first_price_bg) else painterResource(R.drawable.ic_second_price_bg)
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .clickable {}
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
        productRanking = ProductRanking(
            productId = 1,
            productName = "테스트",
            surveyDate = "2024-01-01",
            ranking = listOf(
                RegionRanking(1, "서울", 1000),
                RegionRanking(2, "부산", 1200),
                RegionRanking(3, "대구", 1100)
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PriceMapScreenLoadingPreview() {
    PriceMapScreen(
        isEmptyKeyword = true,
        productRanking = ProductRanking(
            productId = 1,
            productName = "테스트",
            surveyDate = "2024-01-01",
            ranking = emptyList()
        )
    )
}

