package side.dnd.app.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import side.dnd.core.TopLevelRoute
import side.dnd.core.compositionLocals.LocalNavigationActions
import side.dnd.core.compositionLocals.LocalSharedElementTransitionScope
import side.dnd.design.component.VerticalSpacer
import side.dnd.design.component.text.tu
import side.dnd.feature.home.HomeRoute
import side.dnd.feature.home.homeGraph
import com.side.dnd.feature.price_rank.navigation.priceRankGraph

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun NavigationGraph(
    modifier: Modifier = Modifier,
    router: Router,
) {
    val navController = router.navController

    SharedTransitionLayout(modifier = modifier) {
        CompositionLocalProvider(
            LocalSharedElementTransitionScope provides this@SharedTransitionLayout,
            LocalNavigationActions provides router::navigate,
        ) {
            NavHost(
                navController = navController,
                startDestination = HomeRoute.HomeGraph,
                modifier = Modifier.fillMaxSize()
            ) {
                homeGraph()
                priceRankGraph()
            }
        }
    }
}

@Composable
internal fun RowScope.BottomNavigationItems(
    currentDestination: NavDestination?,
    onClick: (TopLevelRoute) -> Unit,
) {
    TopLevelRoutes.forEach { destination ->
        val selected = currentDestination.isDestinationInHierarchy(destination)

        NavigationBarItem(
            selected = selected,
            onClick = { onClick(destination) },
            icon = {
                BottomNavigationIcon(
                    destination = destination,
                    isClicked = selected,
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NavigationDefaults.contentColor(),
                selectedTextColor = NavigationDefaults.contentColor(),
                indicatorColor = NavigationDefaults.navigationIndicatorColor(),
            ),
            modifier = Modifier.height(45.dp)
        )
    }
}

@Composable
private fun BottomNavigationIcon(
    destination: TopLevelRoute,
    isClicked: Boolean,
) {
    val itemColor =
        animateColorAsState(targetValue = if (isClicked) Color(0xFF4D4C4F) else Color(0xFFD8D7D9))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = destination.icon),
            contentDescription = "BottomBar Icon",
            tint = itemColor.value,
            modifier = Modifier.height(20.dp)
        )
        VerticalSpacer(8.dp)
        Text(
            text = destination.description,
            color = itemColor.value,
            fontSize = 12.tu,
            lineHeight = 12.tu
        )
    }
}

@Immutable
internal object NavigationDefaults {
    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.surface

    @Composable
    fun containerColor() = Color.White

    @Composable
    fun contentColor() = MaterialTheme.colorScheme.onSurface
}