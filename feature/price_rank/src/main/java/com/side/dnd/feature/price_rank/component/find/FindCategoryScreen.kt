package com.side.dnd.feature.price_rank.component.find

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun FindCategoryScreen(
    onCategoryClick: (String) -> Unit = {},
    onDetailCategoryClick: (SearchCategory) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    
    val searchCategories = listOf(
        SearchCategory("채소류", R.drawable.ic_category_vegetable),
        SearchCategory("과일류", R.drawable.ic_category_fruit),
        SearchCategory("곡물류", R.drawable.ic_category_food_crops),
        SearchCategory("수산물", R.drawable.ic_category_seafood),
        SearchCategory("축산물", R.drawable.ic_category_livestock_products),
        SearchCategory("특용작물", R.drawable.ic_category_special_crop)
    )
    
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(EodigoColor.White)
            .padding(horizontal = 20.dp),
        topBar = {
            SearchInputField(
                query = searchQuery,
                onQueryChange = { query ->
                    searchQuery = query
                },
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .background(EodigoColor.White)
            )
        }
    ) { paddingValues ->
        CategoryList(
            categories = searchCategories,
            onCategoryClick = { categoryName ->
                onCategoryClick(categoryName)
            },
            onDetailCategoryClick = { category ->
                onDetailCategoryClick(category)
            },
            modifier = Modifier
                .background(EodigoColor.White)
                .padding(paddingValues)
                .padding(top = 32.dp)
        )
    }
}

@Composable
fun CategoryList(
    categories: List<SearchCategory>,
    onCategoryClick: (String) -> Unit,
    onDetailCategoryClick: (SearchCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "카테고리",
            style = EodigoTheme.typography.body1Medium,
            color = EodigoColor.Black,
        )
        Spacer(modifier = Modifier.size(28.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            items(categories) { category ->
                SquareCategoryItem(
                    title = category.title,
                    icon = painterResource(category.icon),
                    onClick = { onDetailCategoryClick(category) }
                )
            }
        }
    }
}

@Composable
fun SearchInputField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color.Black.copy(alpha = 0.6f),
                spotColor = Color.Black.copy(alpha = 0.6f)
            )
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = if (query.isBlank()) EodigoColor.Gray500 else EodigoColor.Black,
                letterSpacing = (-0.75).sp,
                lineHeight = 24.sp
            ),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (query.isBlank()) {
                        Text(
                            text = "자주 사는 물건, 어디가 더 저렴할까요?",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = EodigoColor.Gray500,
                            letterSpacing = (-0.75).sp,
                            lineHeight = 24.sp
                        )
                    }
                    innerTextField()
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "검색",
                        modifier = Modifier.size(24.dp),
                        tint = EodigoColor.Primary
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchInputFieldPreview() {
    SearchInputField(
        query = "",
        onQueryChange = {},
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun FindCategoryScreenPreview() {
    FindCategoryScreen()
}