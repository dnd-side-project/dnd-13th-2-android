package side.dnd.feature.home.store.component

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import side.dnd.design.R
import side.dnd.design.component.HorizontalSpacer
import side.dnd.design.component.SubcomposeAsyncImageWithPreview
import side.dnd.design.component.VerticalSpacer
import side.dnd.design.theme.EodigoTheme
import side.dnd.design.theme.LocalTypography
import side.dnd.design.utils.tu
import side.dnd.feature.home.home.HomeUiState
import side.dnd.feature.home.home.HomeUiStatePreviewParameter
import side.dnd.feature.home.state.Store

@Composable
internal fun StoreList(stores: ImmutableList<Store>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 4.dp)
    ) {
        items(stores, key = { store -> store.name }) { store ->
            StoreCard(store)
        }
    }
}

@Composable
private fun StoreCard(
    store: Store,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .background(Color.White, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier.padding(14.dp)
        ) {
            SubcomposeAsyncImageWithPreview(
                modifier = Modifier.size(100.dp),
                placeHolderPreview = R.drawable.ic_launcher_background,
                model = store.image,
                contentDescription = "Store image",
            )

            HorizontalSpacer(16.dp)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
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
                            SpanStyle(
                                color = Color(0xFF8369FF),
                                fontSize = 18.tu
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${store.distance}m",
                        style = LocalTypography.current.body4Medium,
                        color = Color(0xFF868686),
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
                    )
                }
            }
        }
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
        StoreCard(uiState.stores.first())
    }
}