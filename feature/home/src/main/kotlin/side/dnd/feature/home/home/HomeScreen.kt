package side.dnd.feature.home.home

import android.content.Context
import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.MarkerComposable
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberFusedLocationSource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import side.dnd.core.compositionLocals.LocalAnimatedContentScope
import side.dnd.core.compositionLocals.LocalFABControl
import side.dnd.core.compositionLocals.LocalNavigationActions
import side.dnd.core.compositionLocals.LocalSharedElementTransitionScope
import side.dnd.design.R
import side.dnd.design.component.text.TextFieldWithActionBar
import side.dnd.design.theme.EodigoTheme
import side.dnd.feature.home.BuildConfig
import side.dnd.feature.home.HomeNavigationAction
import side.dnd.feature.home.home.component.MapMarker
import side.dnd.feature.home.home.component.StoreDetailCard

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    context: Context = LocalContext.current,
) {
    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        NaverMapSdk.getInstance(context).client =
            NaverMapSdk.NcpKeyClient(BuildConfig.NAVER_MAP_KEY)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val fusedLocationSource = rememberFusedLocationSource()
    val isTracking by remember(uiState.mapControl.isLocationTracking) {
        derivedStateOf {
            Log.d("test","a: ${uiState.mapControl.isLocationTracking}")
            if (uiState.mapControl.isLocationTracking)
                fusedLocationSource
            else
                null
        }
    }
    val navActions = LocalNavigationActions.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.receiveAsFlow().collectLatest { effect ->
            when (effect) {
                is HomeSideEffect.Navigate -> navActions(effect.action)
            }
        }
    }

    HomeScreen(
        homeUiState = uiState,
        mapComposable = {
            var mapProperties by remember {
                mutableStateOf(
                    MapProperties(
                        maxZoom = 17.0,
                        minZoom = 17.0,
                        locationTrackingMode = LocationTrackingMode.Follow,
                    )
                )
            }
            var mapUiSettings by remember {
                mutableStateOf(
                    MapUiSettings(isLocationButtonEnabled = false)
                )
            }

            NaverMap(
                modifier = Modifier.fillMaxSize(),
                properties = mapProperties,
                uiSettings = mapUiSettings,
                locationSource = isTracking,
            ) {
                uiState.stores.forEachIndexed { idx, store ->
                    key(store.name) {
                        MarkerComposable(
                            state = MarkerState(
                                position = store.latLng.copy(
                                    latitude = store.latLng.latitude + 0.0003 * idx,
                                    longitude = store.latLng.longitude + 0.0003 * idx
                                ).toNaverLatLng()
                            ),
                        ) {
                            MapMarker(
                                store = store,
                                selected = idx == 0
                            )
                        }
                    }
                }

            }
        },
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun HomeScreen(
    homeUiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    mapComposable: @Composable () -> Unit,
) {
    val textFieldState = rememberTextFieldState(initialText = homeUiState.searchWord)
    var dialogVisibility by remember {
        mutableStateOf(false)
    }

    if (dialogVisibility) {
        Dialog(
            onDismissRequest = { dialogVisibility = false }
        ) {
            StoreDetailCard(
                homeUiState.stores.first()
            )
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
    ) {
        if (!LocalInspectionMode.current)
            mapComposable()

        with(LocalSharedElementTransitionScope.current) {
            TextFieldWithActionBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 78.dp, start = 24.dp, end = 24.dp)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState("Search"),
                        animatedVisibilityScope = LocalAnimatedContentScope.current,
                    ),
                textFieldState = textFieldState,
                hint = "오늘의 메뉴는?",
                enabled = false,
                onClickDisabled = {
                    onEvent(HomeEvent.OnSearch)
                }
            )
        }

        IconButton(
            onClick = {
                onEvent(HomeEvent.OnLocationTracking(true))
            },
            modifier = Modifier
                .padding(end = 24.dp, bottom = 58.dp)
                .shadow(9.dp, CircleShape)
                .background(Color.White, CircleShape)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_location_tracker),
                contentDescription = "내 위치",
                modifier = Modifier
                    .size(28.dp),
                tint = Color(0xFF817F84)
            )
        }

        if (homeUiState.searchWord.isNotBlank())
            IconButton(
                onClick = {
                    onEvent(HomeEvent.NavigateToStore)
                },
                modifier = Modifier
                    .padding(start = 24.dp, bottom = 58.dp)
                    .shadow(9.dp, RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .align(Alignment.BottomStart)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_hamburger),
                    contentDescription = "가게 리스트",
                    modifier = Modifier,
                    tint = Color(0xFF817F84)
                )
            }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeScreenPreview(
    @PreviewParameter(HomeUiStatePreviewParameter::class)
    uiState: HomeUiState
) = EodigoTheme {
    PreviewHomeScope {
        HomeScreen(
            homeUiState = uiState,
            onEvent = {},
            mapComposable = {}
        )
    }
}