package side.dnd.feature.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import side.dnd.feature.home.HomeNavigationAction
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val uiState: StateFlow<HomeUiState> field:MutableStateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState.Empty
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
                    sideEffect.send(HomeSideEffect.Navigate(HomeNavigationAction.NavigateToSearch))
                }
            }
        }
    }
}