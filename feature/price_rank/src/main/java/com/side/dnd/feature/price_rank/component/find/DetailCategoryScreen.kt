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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.side.dnd.feature.price_rank.PriceRankViewModel
import com.side.dnd.feature.price_rank.R
import side.dnd.core.local.model.CategoryResponse
import side.dnd.design.component.CategoryText
import side.dnd.design.theme.EodigoColor
import side.dnd.design.theme.EodigoTheme


@Composable
fun DetailCategoryScreen(
    category: CategoryResponse? = null,
    onCategorySelect: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    viewModel: PriceRankViewModel = hiltViewModel()
) {
    var selectedCategory by remember { mutableStateOf("") }
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    
    // 선택된 카테고리의 실제 데이터 찾기
    val selectedCategoryData = categories.find { it.categoryCode == category?.categoryCode }
    
    val categoryName = selectedCategoryData?.categoryName ?: category?.categoryName ?: "식량작물"
    val categoryIcon = getCategoryIcon(selectedCategoryData?.categoryCode ?: category?.categoryCode ?: "100")
    val items = selectedCategoryData?.items ?: category?.items ?: emptyList()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EodigoColor.White)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(84.dp))
        
        CategoryHeader(
            categoryName = categoryName,
            categoryIcon = categoryIcon
        )
        
        Spacer(modifier = Modifier.height(205.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 아이템들을 4개씩 그룹으로 나누어 표시
            items.chunked(4).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowItems.forEach { item ->
                        CategoryText(
                            text = item.itemName,
                            isSelected = selectedCategory == item.itemName,
                            onClick = {
                                selectedCategory = item.itemName
                                onCategorySelect(item.itemName)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // 빈 공간 채우기
                    repeat(4 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSearchClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = EodigoColor.Normal
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "찾아보기",
                style = EodigoTheme.typography.title1Medium.copy(
                    color = EodigoColor.White,
                    fontWeight = FontWeight.Medium
                )
            )
        }
        
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun CategoryHeader(
    categoryName: String,
    categoryIcon: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(124.dp)
                .background(
                    color = EodigoColor.Gray100,
                    shape = RoundedCornerShape(62.dp)
                )
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(categoryIcon),
                contentDescription = categoryName,
                modifier = Modifier.size(84.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(
            text = categoryName,
            style = EodigoTheme.typography.title1Medium.copy(
                color = EodigoColor.Black,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

private fun getCategoryIcon(categoryCode: String): Int {
    return when (categoryCode) {
        "100" -> R.drawable.ic_category_food_crops
        "200" -> R.drawable.ic_category_vegetable
        "300" -> R.drawable.ic_category_special_crop
        "400" -> R.drawable.ic_category_fruit
        "500" -> R.drawable.ic_category_seafood
        "600" -> R.drawable.ic_category_livestock_products
        else -> R.drawable.ic_category_food_crops
    }
}


@Preview(showBackground = true)
@Composable
fun DetailCategoryScreenPreview() {
    DetailCategoryScreen()
}