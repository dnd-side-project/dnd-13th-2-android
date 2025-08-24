package side.dnd.feature.home.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import side.dnd.feature.home.HomeNavigationAction
import side.dnd.feature.home.HomeRoute
import side.dnd.feature.home.home.HomeSideEffect.*
import side.dnd.feature.home.state.Store
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> field:MutableStateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState.Empty.copy(
            searchWord = savedStateHandle.toRoute<HomeRoute.Home>().searchWord,
            stores = persistentListOf(
                Store.DEFAULT.copy(name = "카페 테인1"),
                Store.DEFAULT.copy(name = "카페 테인2"),
                Store.DEFAULT.copy(name = "카페 테인3"),
                Store.DEFAULT.copy(name = "카페 테인4"),
                Store.DEFAULT.copy(name = "카페 테인5"),
            )
        )
    )
    val sideEffect: Channel<HomeSideEffect> = Channel()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnLocationTracking -> {
                uiState.update { state ->
                    state.copy(mapControl = MapControl(isLocationTracking = event.isLocationTracking))
                }
            }

            is HomeEvent.SwitchContentLayout -> {
                uiState.update { state ->
                    state.copy(showSearchContent = event.showSearch)
                }
            }

            HomeEvent.OnSearch -> {
                viewModelScope.launch {
                    sideEffect.send(Navigate(HomeNavigationAction.NavigateToSearch))
                }
            }

            HomeEvent.NavigateToStore -> {
                viewModelScope.launch {
                    sideEffect.send(Navigate(HomeNavigationAction.NavigateToStore(uiState.value.searchWord)))
                }
            }
        }
    }
}