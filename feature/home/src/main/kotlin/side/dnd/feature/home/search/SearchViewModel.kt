package side.dnd.feature.home.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import side.dnd.core.compositionLocals.CommonNavigationAction
import side.dnd.data.network.StoreRepository
import side.dnd.data.network.service.GetStoresRequest
import side.dnd.feature.home.HomeNavigationAction.NavigateToHome
import side.dnd.feature.home.HomeRoute
import side.dnd.feature.home.home.UserMapState
import side.dnd.feature.home.search.SearchSideEffect.Navigate
import side.dnd.feature.home.search.SearchSideEffect.SwitchPage
import side.dnd.feature.home.state.StoreType
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: StoreRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val searchUiState: StateFlow<SearchUiState> field: MutableStateFlow<SearchUiState> = MutableStateFlow(
        SearchUiState.EMPTY
    )
    val userMapState get() = savedStateHandle.toRoute<HomeRoute.Search>(UserMapState.navType).userMapState

    val sideEffect: Channel<SearchSideEffect> = Channel()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSelectChip -> {
                searchUiState.update { state ->
                    state.copy(selectedCategory = event.selectedCategory)
                }
            }

            is SearchEvent.SwitchPage -> {
                viewModelScope.launch {
                    sideEffect.send(SwitchPage(event.storeType.ordinal))
                }
            }

            is SearchEvent.OnBrowse -> {
                viewModelScope.launch {
                    val category = searchUiState.value.selectedCategory
                    repository.getStores(
                        getStoresRequest(
                            menuName = category,
                            category = StoreType.findStoreTypeByCategory(category)
                                ?: StoreType.CAFE,
                            userMapState = userMapState,
                        )
                    )
                    sideEffect.send(Navigate(NavigateToHome))
                }
            }

            SearchEvent.ResetSelectedCategories -> {
                searchUiState.update { state ->
                    state.copy(selectedCategory = "")
                }
            }

            SearchEvent.PopBackStack -> {
                viewModelScope.launch {
                    sideEffect.send(Navigate(CommonNavigationAction.PopBackStack))
                }
            }

            SearchEvent.OnSearch -> {
                viewModelScope.launch {
                    val searchWord = searchUiState.value.textFieldState.text.toString()
                    repository.getStores(
                        getStoresRequest(
                            menuName = searchWord,
                            category = StoreType.findStoreTypeByDisplay(searchWord)
                                ?: StoreType.CAFE,
                            userMapState = userMapState,
                        )
                    )
                    sideEffect.send(Navigate(NavigateToHome))
                }
            }
        }
    }
}

fun getStoresRequest(
    menuName: String,
    category: StoreType,
    userMapState: UserMapState,
): GetStoresRequest =
    GetStoresRequest(
        menuName = menuName,
        category = category.name,
        lat = userMapState.currentLocation.latitude,
        lng = userMapState.currentLocation.longitude,
        westLat = userMapState.southWestLimit.latitude,
        westLng = userMapState.southWestLimit.longitude,
        eastLat = userMapState.northEastLimit.latitude,
        eastLng = userMapState.northEastLimit.longitude
    )
