package side.dnd.feature.home.home

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import side.dnd.data.network.StoreRepository
import side.dnd.feature.home.HomeNavigationAction.NavigateToSearch
import side.dnd.feature.home.HomeNavigationAction.NavigateToStore
import side.dnd.feature.home.home.HomeSideEffect.Navigate
import side.dnd.feature.home.search.getStoresRequest
import side.dnd.feature.home.state.StoreType
import side.dnd.feature.home.state.toStoreImmutableList
import side.dnd.feature.home.store.SortType
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: StoreRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val isLocationTracking: StateFlow<Boolean> =
        savedStateHandle.getStateFlow(IS_LOCATION_TRACKING, false)

    private val userMapState: MutableStateFlow<UserMapState> = MutableStateFlow(UserMapState.EMPTY)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeUiState> = repository.homeCache.map { cache ->
        HomeUiState(
            searchWord = TextFieldState(cache.searchWord),
            stores = cache.stores?.toStoreImmutableList() ?: persistentListOf(),
            isLocationTracking = isLocationTracking.value,
            sortType = SortType.PRICE,
        )
    }.flatMapLatest { uiState ->
        repository.sortType.combine(isLocationTracking) { sortType, isTracking ->
            uiState.copy(
                isLocationTracking = isTracking,
                sortType = SortType.getSortTypeByName(sortType)
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Empty.copy(
            searchWord = TextFieldState("")
        )
    )

    val sideEffect: Channel<HomeSideEffect> = Channel()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnLocationTracking -> {
                savedStateHandle[IS_LOCATION_TRACKING] = event.isLocationTracking
            }

            HomeEvent.OnSearch -> {
                viewModelScope.launch {
                    sideEffect.send(Navigate(NavigateToSearch(userMapState.value)))
                }
            }

            HomeEvent.NavigateToStore -> {
                viewModelScope.launch {
                    sideEffect.send(
                        Navigate(NavigateToStore(userMapState = userMapState.value))
                    )
                }
            }

            is HomeEvent.OnChangedUserMapState -> userMapState.value = event.new

            is HomeEvent.RequestStoresBySortType -> {
                viewModelScope.launch {
                    val searchWord = uiState.value.searchWord.text.toString()

                    repository.updateSortType(event.sortType.name)
                    repository.getStores(
                        getStoresRequest(
                            menuName = searchWord,
                            category = StoreType.findStoreTypeByCategory(searchWord)
                                ?: StoreType.CAFE,
                            userMapState = userMapState.value
                        )
                    )
                }
            }
        }
    }

    companion object {
        const val IS_LOCATION_TRACKING = "isLocationTracking"
    }
}