package side.dnd.design.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavigationTab(
    tabs: List<String>,
    initialPage: Int = 0,
    onPageChanged: (Int) -> Unit = {},
    content: @Composable (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = initialPage
    ) { tabs.size }

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        divider = {},
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                height = 2.dp,
                color = Color(0xFF9B86FC)
            )
        },
        containerColor = Color(0xFFD8D7D9)
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = {
                    Text(
                        text = tab,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                selected = pagerState.currentPage == index,
                selectedContentColor = Color(0xFF3A393B),
                unselectedContentColor = Color(0xFFD8D7D9),
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                        onPageChanged(index)
                    }
                }
            )
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { page ->
        content(page)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomNavigationTabPreview() {
    CustomNavigationTab(
        tabs = listOf("Tab 1", "Tab 2"),
        content = { page ->
            when (page) {
                0 -> Text("Content for Tab 1")
                1 -> Text("Content for Tab 2")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}