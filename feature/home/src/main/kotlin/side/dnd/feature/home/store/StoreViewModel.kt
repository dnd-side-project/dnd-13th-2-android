package side.dnd.feature.home.store

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import side.dnd.core.compositionLocals.CommonNavigationAction
import side.dnd.data.network.StoreRepository
import side.dnd.feature.home.HomeNavigationAction
import side.dnd.feature.home.HomeRoute
import side.dnd.feature.home.home.UserMapState
import side.dnd.feature.home.search.getStoresRequest
import side.dnd.feature.home.state.StoreType
import side.dnd.feature.home.state.toStoreImmutableList
import side.dnd.feature.home.store.StoreSideEffect.Navigation
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val repository: StoreRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<StoreUiState> = repository.homeCache.map { cache ->
        StoreUiState(
            searchWord = cache.searchWord,
            stores = cache.stores?.toStoreImmutableList() ?: persistentListOf(),
            selectedSortType = SortType.PRICE
        )
    }.combine(repository.sortType) { uiState, sortType ->
        uiState.copy(
            selectedSortType = SortType.getSortTypeByName(sortType),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = StoreUiState.DEFAULT
    )

    private val userMapState get() = savedStateHandle.toRoute<HomeRoute.Store>(UserMapState.navType).userMapState

    val sideEffect: Channel<StoreSideEffect> = Channel()

    fun onEvent(event: StoreEvent) {
        when (event) {
            is StoreEvent.SelectSortType -> {
                viewModelScope.launch {
                    repository.updateSortType(event.sortType.name)
                    repository.getStores(
                        getStoresRequest(
                            menuName = uiState.value.searchWord,
                            category = StoreType.findStoreTypeByDisplay(uiState.value.searchWord)
                                ?: StoreType.CAFE,
                            userMapState = userMapState,
                        )
                    )
                }
            }

            is StoreEvent.PopBackStack -> {
                sideEffect.trySend(Navigation(CommonNavigationAction.PopBackStack))
            }

            StoreEvent.OnSearch -> viewModelScope.launch {
                sideEffect.trySend(Navigation(HomeNavigationAction.NavigateToSearch(userMapState)))
            }
        }
    }
}