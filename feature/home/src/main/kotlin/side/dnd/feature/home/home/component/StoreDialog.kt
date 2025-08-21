package side.dnd.feature.home.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import side.dnd.design.R
import side.dnd.design.component.HorizontalSpacer
import side.dnd.design.component.HorizontalWeightSpacer
import side.dnd.design.component.SubcomposeAsyncImageWithPreview
import side.dnd.design.component.VerticalSpacer
import side.dnd.design.component.text.tu
import side.dnd.design.theme.EodigoTheme
import side.dnd.feature.home.home.Store

@Composable
fun StoreDetailCard(
    store: Store,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
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
                )
        )

        VerticalSpacer(27.dp)

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = store.name,
                style = TextStyle(
                    fontSize = 20.tu,
                    fontWeight = FontWeight.W400,
                    color = Color.Black
                )
            )
            HorizontalSpacer(4.dp)
            Text(
                text = store.type.display,
                style = TextStyle(
                    fontSize = 14.tu,
                    fontWeight = FontWeight.W400,
                    color = Color(0xFFA6A6A6)
                )
            )
        }

        VerticalSpacer(16.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${store.distance}m",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF868686)
                    )
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
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF868686)
                    )
                )
            }

            HorizontalWeightSpacer(1f)
            Text(
                text = "${store.price}원",
                modifier = Modifier
                    .padding(bottom = 4.dp),
                style = TextStyle(
                    fontSize = 20.tu,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            )
        }

        VerticalSpacer(8.dp)
    }
}

@Preview(showBackground = false)
@Composable
fun StoreItemPreview() = EodigoTheme {
    Column(
        modifier = Modifier
            .width(400.dp)
            .height(300.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        StoreDetailCard(
            store = Store.DEFAULT
        )
    }
}