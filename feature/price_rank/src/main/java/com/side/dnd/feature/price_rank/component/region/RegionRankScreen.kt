package com.side.dnd.feature.price_rank.component.region

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.side.dnd.feature.price_rank.R
import com.side.dnd.feature.price_rank.model.MockProductRanking
import com.side.dnd.feature.price_rank.model.NationWideUiState
import com.side.dnd.feature.price_rank.model.RegionRanking
import side.dnd.design.component.LocalRankRow
import side.dnd.design.theme.EodigoColor
import side.dnd.design.theme.EodigoTheme
import side.dnd.design.R as UI

@Composable
fun RegionRankScreen(
    uiState: NationWideUiState,
    modifier: Modifier = Modifier
) {
    val listingState = rememberLazyListState()
    var isExpanded by remember { mutableStateOf(false) }

    val productRanking = uiState.productRanking
    val isEmptyKeyword = uiState.keyWord.isEmpty()

    val rankingList = if (isEmptyKeyword) {
        MockProductRanking.emptyProductRanking.ranking.mapIndexed { index, regionRanking ->
            regionRanking.copy(rank = index + 1)
        }
    } else {
        productRanking.ranking.mapIndexed { index, regionRanking ->
            regionRanking.copy(rank = index + 1)
        }
    }
    val bgColor = if (isExpanded) EodigoColor.Light else EodigoColor.White

    LazyColumn(
        state = listingState,
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(bgColor)
            ) {
                Spacer(modifier = Modifier.size(52.dp))

                if (isEmptyKeyword) {
                    Text(
                        text = stringResource(id = R.string.price_map_empty_title),
                        style = EodigoTheme.typography.body3Medium,
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
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(id = R.string.price_map_title_first),
                            style = EodigoTheme.typography.body3Medium,
                            color = EodigoColor.Gray900,
                        )
                        Text(
                            text = " \"${productRanking.productName}\" ",
                            style = EodigoTheme.typography.body3Medium,
                            color = EodigoColor.Primary
                        )
                        Text(
                            text = stringResource(id = R.string.price_map_title_second),
                            style = EodigoTheme.typography.body3Medium,
                            color = EodigoColor.Gray900,
                        )
                    }
                    Spacer(modifier = Modifier.size(5.dp))

                    Text(
                        text = productRanking.ranking.first().regionName,
                        style = EodigoTheme.typography.title1Medium,
                        color = EodigoColor.Black,
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
                if (!isEmptyKeyword && !isExpanded) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                isExpanded = !isExpanded
                            }
                    ) {
                        Text(
                            text = "더보기",
                            style = EodigoTheme.typography.body3Medium,
                            color = EodigoColor.Gray500,
                        )
                        Spacer(modifier = Modifier.height(15.dp))

                        Image(
                            painter = painterResource(R.drawable.ic_arrow_right),
                            contentDescription = null,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))

                if (!isExpanded) {
                    RankingBarComponent(
                        rankItems = rankingList,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .then(
                                if (isEmptyKeyword) Modifier.blur(3.dp)
                                else Modifier
                            )
                    )

                    Spacer(modifier = Modifier.height(22.dp))
                }



                Spacer(modifier = Modifier.height(22.dp))
            }
        }

        if (isExpanded) {
            items(rankingList) { product ->
                LocalRankRow(
                    rank = product.rank,
                    locationName = product.regionName,
                    price = product.price,

                    modifier = Modifier
                        .padding(start = 22.dp, end = 22.dp, top = 10.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .background(EodigoColor.Light)
                        .then(
                            if (isEmptyKeyword) Modifier.blur(3.dp)
                            else Modifier
                        )
                )
            }
        } else if (rankingList.size > 3 || isEmptyKeyword) {
            items(rankingList.subList(3, minOf(10, rankingList.size))) { product ->

                LocalRankRow(
                    rank = product.rank,
                    locationName = product.regionName,
                    price = product.price,
                    modifier = Modifier
                        .padding(start = 22.dp, end = 22.dp, top = 10.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .background(EodigoColor.Light)
                        .then(
                            if (isEmptyKeyword) Modifier.blur(3.dp)
                            else Modifier
                        )
                )
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
fun RegionRankScreenPreview() {
    RegionRankScreen(
        uiState = NationWideUiState.Empty
    )
}


@Composable
fun RankingBarComponent(
    rankItems: List<RegionRanking>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        RegionRankBarItem(
            item = rankItems[1],
            modifier = Modifier.weight(1f)
        )
        RegionRankBarItem(
            item = rankItems.first(),
            modifier = Modifier.weight(1f)
        )
        RegionRankBarItem(
            item = rankItems[2],
            modifier = Modifier.weight(1f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun RankingBarComponentPreview() {
    RankingBarComponent(
        rankItems = listOf(
            RegionRanking(1, "서울", 1000),
            RegionRanking(2, "부산", 1200),
            RegionRanking(3, "대구", 1100)
        ),
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun RegionRankBarItemPreview() {
    RegionRankBarItem(
        item = RegionRanking(
            rank = 2,
            regionName = "지역명지역",
            price = 10900
        ),
        modifier = Modifier.padding(16.dp)
    )
}


@Composable
fun RegionRankBarItem(
    item: RegionRanking,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (item.rank == 1) {
        EodigoColor.NormalLight
    } else {
        EodigoColor.Gray100
    }

    val priceColor = if (item.rank == 1) {
        EodigoColor.Primary2
    } else {
        EodigoColor.Gray600
    }

    val rankNumberColor = if (item.rank == 1) {
        EodigoColor.Gold
    } else {
        EodigoColor.Bronze
    }

    val height = if (item.rank == 1) {
        85.dp
    } else {
        55.dp
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (item.rank == 1) {
                Image(
                    painter = painterResource(id = R.drawable.ic_crown),
                    contentDescription = null,
                    Modifier.size(45.dp)
                )
            }
            Text(
                text = item.regionName,
                style = EodigoTheme.typography.body1Medium,
                color = Color(0xFF2D2C2E)
            )

            Text(
                text = "${item.price}원",
                style = EodigoTheme.typography.body1Medium,
                color = priceColor
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(
                    color = backgroundColor,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp
                    )
                )
                .padding(horizontal = 36.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color.Transparent,
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                when (item.rank) {
                    1 -> {
                        Image(
                            painter = painterResource(id = UI.drawable.ic_first_grade),
                            contentDescription = null
                        )
                    }

                    2 -> {
                        Text(
                            text = item.rank.toString(),
                            style = EodigoTheme.typography.body1Medium,
                            color = rankNumberColor
                        )
                    }

                    3 -> {
                        Image(
                            painter = painterResource(id = UI.drawable.ic_third_grade),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }

}