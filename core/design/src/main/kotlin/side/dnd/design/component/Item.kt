package side.dnd.design.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import side.dnd.design.R

@Composable
fun LocalRankRow(
    rank: Int,
    locationName: String,
    price: Int,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .width(200.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(horizontal = 14.dp, vertical = 7.dp),
            contentAlignment = Alignment.Center
        ) {
            if (visible) {
                Image(
                    painter = painterResource(R.drawable.ic_rank_bg),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
            Text(
                text = rank.toString(),
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9B86FC),
                    textAlign = TextAlign.Center,
                    letterSpacing = (-0.75).sp
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = locationName,
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2D2C2E),
                    letterSpacing = (-0.75).sp
                )
            )
            
            Text(
                text = "${price}원",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF817F84),
                    letterSpacing = (-0.75).sp
                )
            )
        }
    }
}

@Composable
fun CircleCategoryItem(
    title: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    shape = CircleShape,
                    spotColor = Color.Black.copy(alpha = 0.1f)
                )
                .clickable(onClick = onClick)
                .size(56.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape
                )

        ) {
            Image(
                painter = icon,
                contentDescription = title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)

            )
        }
        
        Spacer(modifier = Modifier.height(17.dp))
        
        Text(
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFF5F3FF),
                textAlign = TextAlign.Center,
                letterSpacing = (-0.7).sp
            )
        )
    }
}

@Composable
fun StoreItem(
    storeName: String,
    category: String,
    distance: String,
    location: String,
    price: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column {
            AsyncImage(
                model = "https://example.com/image.jpg",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .height(85.dp)
                    .background(
                        color = Color(0xFFD9D9D9),
                        shape = RoundedCornerShape(16.dp)
                    )
            )

            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = storeName,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = category,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFA6A6A6)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = distance,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF868686)
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(2.dp)
                            .background(Color(0xFF868686))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = location,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF868686)
                        )
                    )
                }

                Text(
                    text = "${price}원",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoreItemPreview() {
    StoreItem(
        storeName = "카페 테인",
        category = "카페",
        distance = "202m",
        location = "서울시 광진구 자양동",
        price = 2500
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF9B86FC)
@Composable
fun CategoryItemPreview() {
    CircleCategoryItem(
        title = "찾아보기",
        icon = painterResource(R.drawable.ic_home)
    )
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun LocalRankRowPreview() {
    LocalRankRow(
        rank = 4,
        locationName = "지역명이 들어가요!",
        price = 1200
    )
}