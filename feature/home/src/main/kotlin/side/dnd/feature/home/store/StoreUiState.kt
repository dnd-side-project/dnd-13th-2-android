package side.dnd.feature.home.store

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import side.dnd.core.compositionLocals.NavigationAction
import side.dnd.feature.home.state.Store

data class StoreUiState(
    val searchWord: String,
    val stores: ImmutableList<Store>,
    val selectedSortType: SortType,
) {
    companion object {
        val DEFAULT = StoreUiState(
            searchWord = "카페 테인",
            stores = persistentListOf(
                Store.DEFAULT,
                Store.DEFAULT,
                Store.DEFAULT,
                Store.DEFAULT,
                Store.DEFAULT,
            ),
            selectedSortType = SortType.PRICE,
        )
    }
}

sealed class StoreEvent {
    data class Navigation(val action: NavigationAction) : StoreEvent()
    data class SelectSortType(val sortType: SortType) : StoreEvent()
}

sealed class StoreSideEffect {
    data class Navigation(val action: NavigationAction): StoreSideEffect()
}

enum class SortType(val display: String) {
    PRICE("가격순"),
    DISTANCE("거리순");

    companion object {
        fun findByDisplay(display: String): SortType = entries.first { it.display == display }
    }
}