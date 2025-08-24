package side.dnd.feature.home.search

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import side.dnd.feature.home.state.StoreType

class SearchUiStatePreviewParameter : PreviewParameterProvider<SearchUiState> {
    override val values: Sequence<SearchUiState> get() = sequenceOf(
        SearchUiState.EMPTY.copy(
            selectedCategory = StoreType.CAFE.category.first(),
        ),
        run {
            val category = StoreType.RESTAURANT

            SearchUiState.EMPTY.copy(
                selectedCategory = category.category.first(),
            )
        },
    )
}