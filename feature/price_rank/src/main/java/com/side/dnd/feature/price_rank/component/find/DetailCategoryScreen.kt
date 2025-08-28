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
import com.side.dnd.feature.price_rank.R
import side.dnd.design.component.CategoryText
import side.dnd.design.theme.EodigoColor
import side.dnd.design.theme.EodigoTheme

@Composable
fun DetailCategoryScreen(
    onCategorySelect: (String) -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf("") }
    
    val categoryName = "채소류"
    val categoryIcon = R.drawable.ic_category_vegetable
    
    val items = listOf(
        "배추", "양배추", "상추", "시금치",
        "당근", "무", "양파", "마늘",
        "고추", "토마토", "오이", "가지"
    )

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
            items.chunked(4).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowItems.forEach { item ->
                        CategoryText(
                            text = item,
                            isSelected = selectedCategory == item,
                            onClick = {
                                selectedCategory = item
                                onCategorySelect(item)
                            },
                            modifier = Modifier.weight(1f)
                        )
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

@Preview(showBackground = true)
@Composable
fun DetailCategoryScreenPreview() {
    DetailCategoryScreen()
}