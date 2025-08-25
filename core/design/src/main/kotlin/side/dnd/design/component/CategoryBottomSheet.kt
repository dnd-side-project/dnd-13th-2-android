package side.dnd.design.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import side.dnd.design.R

data class CategoryItem(
    val name: String,
    val iconRes: Int
)

@Composable
fun CategoryBottomSheet(
    visible: Boolean,
    onCategoryClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        CategoryItem("찾아보기", R.drawable.ic_serach_black),
        CategoryItem("삼겹살", R.drawable.ic_search_meat),
        CategoryItem("계란", R.drawable.ic_search_egg),
        CategoryItem("쌀", R.drawable.ic_search_rice),
        CategoryItem("흰우유", R.drawable.ic_search_milk)
    )

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp)
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                items(categories) { category ->
                    CircleCategoryItem(
                        title = category.name,
                        icon = painterResource(category.iconRes),
                        onClick = { onCategoryClick(category.name) }
                    )
                }
            }
        }
    }
}
