package side.dnd.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTonalElevationEnabled
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import side.dnd.app.navigation.BottomNavigationItems
import side.dnd.app.navigation.NavigationDefaults
import side.dnd.app.navigation.NavigationGraph
import side.dnd.app.navigation.rememberRouter
import side.dnd.core.SnackBarMessage
import side.dnd.core.compositionLocals.LocalShowSnackBar
import side.dnd.design.theme.EodigoTheme
import side.dnd.design.component.CircularFAB

@HiltAndroidApp
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EodigoTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content(
        navController: NavHostController = rememberNavController(),
    ) {
        val snackBarHostState = remember { SnackbarHostState() }

        val snackBarChannel = remember {
            Channel<SnackBarMessage>(Channel.CONFLATED)
        }

        LaunchedEffect(key1 = snackBarChannel) {
            snackBarChannel.receiveAsFlow().collect { snackBarMessage ->
                snackBarHostState.currentSnackbarData?.let {
                    snackBarHostState.showSnackbar(
                        message = snackBarMessage.headerMessage,
                        actionLabel = snackBarMessage.contentMessage,
                        duration = SnackbarDuration.Indefinite,
                    )
                }
            }
        }

        val router = rememberRouter(navController = navController)
        val currentDestination by rememberUpdatedState(newValue = router.currentDestination)

        CompositionLocalProvider(
            LocalTonalElevationEnabled provides false,
            LocalShowSnackBar provides { snackBarMessage: SnackBarMessage ->
                snackBarChannel.trySend(snackBarMessage)
            }
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = WindowInsets.statusBars.asPaddingValues()
                            .run { calculateTopPadding() + calculateBottomPadding() }),
                bottomBar = {
                    CircularFAB(modifier = Modifier.fillMaxWidth()) {
                        NavigationBar(
                            containerColor = NavigationDefaults.containerColor(),
                            contentColor = NavigationDefaults.contentColor(),
                            tonalElevation = 0.dp,
                            modifier = Modifier
                                .shadow(
                                    10.dp, RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp
                                    )
                                )
                                .align(Alignment.BottomCenter),
                        ) {
                            BottomNavigationItems(
                                currentDestination = currentDestination,
                                onClick = { topLevelRoute ->
                                    router.navigateTopLevelDestination(topLevelRoute)
                                }
                            )
                        }
                    }
                }
            ) { paddingValues ->
                NavigationGraph(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = paddingValues.calculateBottomPadding(),
                            start = paddingValues.calculateStartPadding(LayoutDirection.Rtl),
                            end = paddingValues.calculateStartPadding(LayoutDirection.Rtl),
                        ),
                    router = router,
                )
            }
        }
    }

    @Preview
    @Composable
    fun MainScreenPreview() = EodigoTheme {
        Content()
    }
}