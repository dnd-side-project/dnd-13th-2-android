package side.dnd.feature.home.store

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.persistentListOf
import side.dnd.feature.home.state.Store

class StoreUiStatePreviewParameter: PreviewParameterProvider<StoreUiState> {
    override val values: Sequence<StoreUiState> get() = sequenceOf(
        StoreUiState(
            searchWord = "아메리카노",
            stores = persistentListOf(
                Store.DEFAULT.copy(name = "카페 테인1"),
                Store.DEFAULT.copy(name = "카페 테인2"),
                Store.DEFAULT.copy(name = "카페 테인3"),
                Store.DEFAULT.copy(name = "카페 테인4"),
                Store.DEFAULT.copy(name = "카페 테인5"),
            ),
            selectedSortType = SortType.PRICE
        )
    )
}