package side.dnd.feature.home.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel(){

    val uiState: StateFlow<HomeUiState> field:MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Empty)

    fun onEvent(event: HomeEvent){
        when(event) {
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
        }
    }

    sealed class HomeEvent {
        data class OnLocationTracking(val isLocationTracking: Boolean) : HomeEvent()
        data class SwitchContentLayout(val showSearch: Boolean): HomeEvent()
    }
}