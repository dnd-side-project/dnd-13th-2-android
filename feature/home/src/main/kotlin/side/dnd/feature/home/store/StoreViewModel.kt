package side.dnd.feature.home.store

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import side.dnd.feature.home.HomeRoute
import side.dnd.feature.home.state.Store
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val uiState: StateFlow<StoreUiState> field: MutableStateFlow<StoreUiState> = MutableStateFlow(
        StoreUiState.DEFAULT.copy(
            stores = persistentListOf(
                Store.DEFAULT.copy(name = "카페 테인1"),
                Store.DEFAULT.copy(name = "카페 테인2"),
                Store.DEFAULT.copy(name = "카페 테인3"),
                Store.DEFAULT.copy(name = "카페 테인4"),
                Store.DEFAULT.copy(name = "카페 테인5"),
                Store.DEFAULT.copy(name = "카페 테인6"),
                Store.DEFAULT.copy(name = "카페 테인7"),
                Store.DEFAULT.copy(name = "카페 테인8"),
                Store.DEFAULT.copy(name = "카페 테인9"),
            ),
            searchWord = savedStateHandle.toRoute<HomeRoute.Store>().searchWord
        )
    )

    val sideEffect: Channel<StoreSideEffect> = Channel()

    fun onEvent(event: StoreEvent) {
        when (event) {
            is StoreEvent.SelectSortType -> {
                //TODO 저장소에 sortType 저장 후, sort 된 데이터 요청
                uiState.update { state ->
                    state.copy(selectedSortType = event.sortType)
                }
            }

            is StoreEvent.Navigation -> {
                sideEffect.trySend(StoreSideEffect.Navigation(event.action))
            }
        }
    }
}