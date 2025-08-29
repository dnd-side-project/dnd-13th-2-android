package side.dnd.feature.home.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import side.dnd.design.R
import side.dnd.design.component.HorizontalSpacer
import side.dnd.design.component.HorizontalWeightSpacer
import side.dnd.design.component.SubcomposeAsyncImageWithPreview
import side.dnd.design.component.VerticalSpacer
import side.dnd.design.component.progress.ProgressIndicatorRotating
import side.dnd.design.theme.EodigoTheme
import side.dnd.design.theme.LocalTypography
import side.dnd.design.utils.tu
import side.dnd.feature.home.state.Store

@Composable
fun StoreDetailCard(
    store: Store,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("error_black_cat.json"))
    val lottieProgress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    Column(
        modifier = modifier
            .padding(top = 250.dp)
            .fillMaxWidth()
            .height(218.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {
        SubcomposeAsyncImageWithPreview(
            placeHolderPreview = R.drawable.ic_launcher_background,
            model = store.image,
            contentDescription = "Store Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)
                .clip(
                    shape = RoundedCornerShape(16.dp)
                ),
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
            contentScale = ContentScale.FillWidth,
        )

        VerticalSpacer(27.dp)

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = store.name,
                style = LocalTypography.current.title2Medium.copy(
                    fontSize = 20.tu
                )
            )
            HorizontalSpacer(4.dp)
            Text(
                text = store.type.display,
                style = LocalTypography.current.body4Medium,
                color = Color(0xFFB8B7B9)
            )

            HorizontalWeightSpacer(1f)

            Text(
                text = store.menu,
                style = LocalTypography.current.body2Medium.copy(
                    lineHeight = 16.tu,
                    fontSize = 16.tu
                ),
                modifier = Modifier
            )
        }

        VerticalSpacer(12.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${store.distance}m",
                style = LocalTypography.current.body4Medium.copy(
                    fontSize = 14.tu
                ),
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
                style = LocalTypography.current.body4Medium.copy(
                    fontSize = 14.tu
                ),
                color = Color(0xFF868686),
            )

            HorizontalWeightSpacer(1f)

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF7050FF),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.tu
                        )
                    ) {
                        append("${store.price}")
                    }
                    append("원")
                },
                modifier = Modifier,
                style = LocalTypography.current.body2Medium.copy(
                    fontSize = 16.tu,
                    fontWeight = FontWeight.W500
                ),
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun StoreItemPreview() = EodigoTheme {
    Column(
        modifier = Modifier
            .width(400.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
    ) {
        StoreDetailCard(
            store = Store.DEFAULT
        )
    }
}