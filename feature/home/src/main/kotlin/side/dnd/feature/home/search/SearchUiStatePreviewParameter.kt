package side.dnd.feature.home.search

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.persistentMapOf

class SearchUiStatePreviewParameter : PreviewParameterProvider<SearchUiState> {
    override val values: Sequence<SearchUiState> = sequenceOf(
        SearchUiState.EMPTY.copy(
            categoryPointer = "한식",
            categories = SearchUiState.mockCategories,
            selectedCategories = linkedMapOf("최상위" to "한식")
        ),
        SearchUiState.EMPTY.copy(
            categoryPointer = "찌개",
            categories = SearchUiState.mockCategories,
            selectedCategories = linkedMapOf("최상위" to "한식", "한식" to "찌개")
        ),
        SearchUiState.EMPTY.copy(
            categoryPointer = "김치찌개",
            categories = SearchUiState.mockCategories,
            selectedCategories = linkedMapOf("최상위" to "한식", "한식" to "찌개", "찌개" to "김치찌개")
        ),
    )
}