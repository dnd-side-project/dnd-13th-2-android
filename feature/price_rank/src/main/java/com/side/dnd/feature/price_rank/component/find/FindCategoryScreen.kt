package com.side.dnd.feature.price_rank.component.find

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.side.dnd.feature.price_rank.R
import side.dnd.design.component.SquareCategoryItem
import side.dnd.design.theme.EodigoColor
import side.dnd.design.theme.EodigoTheme

data class SearchCategory(
    val title: String,
    val icon: Int
)

@Composable
fun FindCategoryScreen() {
    val categoryList = listOf(
        SearchCategory("식량작물", R.drawable.ic_category_food_crops),
        SearchCategory("채소", R.drawable.ic_category_vegetable),
        SearchCategory("특용작물", R.drawable.ic_category_special_crop),
        SearchCategory("과일", R.drawable.ic_category_fruit),
        SearchCategory("수산물", R.drawable.ic_category_seafood),
        SearchCategory("축산물", R.drawable.ic_category_livestock_products),
    )
    val topRowItems = categoryList.take(4)
    val bottomRowItems = categoryList.drop(4).take(2)
    Scaffold(
        modifier = Modifier
            .background(EodigoColor.White)
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        topBar = {
            SearchInputField(
                modifier = Modifier
                    .background(EodigoColor.White)
                    .padding(vertical = 24.dp)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(EodigoColor.White)
                .padding(paddingValues)
                .padding(top = 32.dp)
        ) {
            Text(
                text = "카테고리",
                style = EodigoTheme.typography.body1Medium,
                color = EodigoColor.Black,

            )
            Spacer(modifier = Modifier.size(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                topRowItems.map {
                    SquareCategoryItem(
                        title = it.title,
                        icon = painterResource(it.icon)
                    )
                }
            }
            Spacer(modifier = Modifier.size(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                bottomRowItems.map {
                    SquareCategoryItem(
                        title = it.title,
                        icon = painterResource(it.icon)
                    )
                }
            }
        }

    }
}

@Composable
fun SearchInputField(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color.Black.copy(alpha = 0.08f),
                spotColor = Color.Black.copy(alpha = 0.08f)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "자주 사는 물건, 어디가 더 저렴할까요?",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = EodigoColor.Gray500,
                letterSpacing = (-0.75).sp,
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "검색",
                modifier = Modifier.size(24.dp),
                tint = EodigoColor.Primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchInputFieldPreview() {
    SearchInputField(
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun FindCategoryScreenPreview() {
    FindCategoryScreen()
}