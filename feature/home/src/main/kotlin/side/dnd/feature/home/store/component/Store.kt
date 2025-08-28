package side.dnd.feature.home.store.component

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.collections.immutable.ImmutableList
import side.dnd.design.R
import side.dnd.design.component.HorizontalSpacer
import side.dnd.design.component.SubcomposeAsyncImageWithPreview
import side.dnd.design.component.VerticalSpacer
import side.dnd.design.component.progress.ProgressIndicatorRotating
import side.dnd.design.theme.EodigoTheme
import side.dnd.design.theme.LocalTypography
import side.dnd.design.utils.tu
import side.dnd.feature.home.home.HomeUiState
import side.dnd.feature.home.home.HomeUiStatePreviewParameter
import side.dnd.feature.home.state.Store
import side.dnd.feature.home.store.SortType

@Composable
internal fun StoreList(
    stores: ImmutableList<Store>,
    sortType: SortType,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        itemsIndexed(stores, key = { idx, store -> store.name }) { idx, store ->
            StoreCard(
                store = store,
                sortType = sortType,
                isFirst = idx == 0,
            )
        }
    }
}

@Composable
private fun StoreCard(
    store: Store,
    sortType: SortType,
    isFirst: Boolean,
    context: Context = LocalContext.current,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("error_black_cat.json"))
    val lottieProgress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(14.dp),
    ) {
        SubcomposeAsyncImageWithPreview(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            placeHolderPreview = R.drawable.ic_launcher_background,
            model = ImageRequest.Builder(context).data(store.image).build(),
            loading = {
                ProgressIndicatorRotating()
            },
            error = {
                LottieAnimation(
                    composition = composition,
                    progress = { lottieProgress },
                    modifier = Modifier.height(300.dp)
                )
            },
            contentDescription = "Store image",
            contentScale = ContentScale.Crop,
        )

        HorizontalSpacer(16.dp)

        Column(
            modifier = Modifier
                .padding(top = 2.dp, bottom = 3.dp),
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = store.name,
                style = LocalTypography.current.body1Medium,
            )
            VerticalSpacer(33.dp)
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        if (isFirst && sortType == SortType.PRICE)
                            SpanStyle(
                                color = Color(0xFF8369FF),
                                fontSize = 18.tu,
                                fontWeight = FontWeight.Bold,
                            )
                        else
                            SpanStyle(
                                color = Color(0xFF817F84),
                                fontSize = 18.tu,
                            )
                    ) {
                        append("${store.price}")
                    }
                    append("원")
                },
                style = LocalTypography.current.body2Medium,
            )

            VerticalSpacer(7.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            if (isFirst && sortType == SortType.DISTANCE)
                                SpanStyle(
                                    color = Color(0xFF8369FF),
                                    fontWeight = FontWeight.Bold,
                                )
                            else
                                SpanStyle(
                                    color = Color(0xFF817F84),
                                )
                        ) {
                            append("${store.distance}m")
                        }
                    },
                    style = LocalTypography.current.body4Medium,
                    color = Color(0xFF868686),
                    modifier = Modifier.alignByBaseline()
                )
                HorizontalSpacer(4.dp)
                Canvas(Modifier.size(2.dp)) {
                    drawCircle(
                        color = Color(0xFF868686)
                    )
                }
                HorizontalSpacer(4.dp)
                Text(
                    text = store.address,
                    style = LocalTypography.current.body4Medium,
                    color = Color(0xFF868686),
                    modifier = Modifier.alignByBaseline(),
                    maxLines = 1,
                )
            }
        }
    }

}

@Composable
@Preview
private fun PreviewStoreCardFirst(
    @PreviewParameter(HomeUiStatePreviewParameter::class)
    uiState: HomeUiState
) = EodigoTheme {
    Column(
        modifier = Modifier
            .width(400.dp)
            .height(200.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
    ) {
        StoreCard(
            store = uiState.stores.first(),
            sortType = SortType.PRICE,
            isFirst = true,
        )
    }
}

@Composable
@Preview
private fun PreviewStoreCard(
    @PreviewParameter(HomeUiStatePreviewParameter::class)
    uiState: HomeUiState
) = EodigoTheme {
    Column(
        modifier = Modifier
            .width(400.dp)
            .height(200.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
    ) {
        StoreCard(
            store = uiState.stores.first(),
            sortType = SortType.DISTANCE,
            isFirst = false,
        )
    }
}