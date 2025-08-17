package side.dnd.feature.home.search

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

@Stable
data class SearchUiState(
    val isSearchReady: Boolean,
    val categoryPointer: String,
    val categories: ImmutableMap<String, ImmutableList<String>>,
    val selectedTab: SearchTab,
    val selectedCategories: LinkedHashMap<String, String> = linkedMapOf(),
) {
    val pageCount: Int = 2

    companion object {
        val EMPTY = SearchUiState(
            isSearchReady = false,
            categoryPointer = "최상위",
            categories = persistentMapOf(),
            selectedTab = SearchTab.CAFE,
        )
        val mockCategories = persistentMapOf(
            "최상위" to persistentListOf("한식", "일식", "양식"),
            "한식" to persistentListOf("찌개", "볶음밥", "탕"),
            "일식" to persistentListOf("돈까스", "라멘", "초밥"),
            "양식" to persistentListOf("피자", "파스타", "스테이크"),
            "찌개" to persistentListOf("된장찌개", "김치찌개", "순두부찌개"),
            "볶음밥" to persistentListOf("김치볶음밥", "오징어볶음밥", "잡채볶음밥"),
            "탕" to persistentListOf("삼계탕", "갈비탕", "설렁탕"),
            "돈까스" to persistentListOf("치즈돈까스", "고구마돈까스", "새우돈까스"),
            "라멘" to persistentListOf("쇼유라멘", "미소라멘", "땡초라멘"),
            "초밥" to persistentListOf("연어초밥", "참치초밥", "장어초밥"),
            "피자" to persistentListOf("페퍼로니피자", "불고기피자", "콤비네이션피자"),
            "파스타" to persistentListOf("토마토파스타", "크림파스타", "알리오올리오"),
            "스테이크" to persistentListOf("안심스테이크", "등심스테이크", "립아이스테이크"),
        )
    }
}

sealed class SearchEvent {
    data class SwitchPage(val page: Int) : SearchEvent()
    data class OnSelectChip(val idx: Int, val mainCategory: String, val subCategory: String) :
        SearchEvent()
}

data class Category(
    val label: String?,
    val subCategory: ImmutableList<Category>,
)


enum class SearchTab(val display: String) {
    CAFE("카페"),
    RESTAURANT("음식점");
}