package side.dnd.app.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import side.dnd.core.TopLevelRoute
import side.dnd.design.R
import side.dnd.design.component.VerticalSpacer
import side.dnd.design.component.text.tu

@Composable
internal fun NavigationGraph(
    modifier: Modifier = Modifier,
    router: Router,
) {
    val navController = router.navController

    NavHost(
        navController = navController,
        startDestination = TempRoute.Temp, //TODO 지워야 함!!!
        modifier = modifier
    ) {
        composable<TempRoute.Temp> {
            Column {

            }

        }

        composable<TempRoute2.Temp> {
            Column {

            }
        }
    }
}

//TODO 지워야 함!!!
@Serializable
sealed class TempRoute {
    @Serializable
    data object Temp : TempRoute(), TopLevelRoute {
        override val icon: Int = R.drawable.ic_home
        override val description: String = "홈"
    }
}
//TODO 지워야 함!!!
@Serializable
sealed class TempRoute2 {
    @Serializable
    data object Temp : TempRoute(), TopLevelRoute {
        override val icon: Int = R.drawable.ic_price_ranking
        override val description: String = "가격 랭킹"
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
    fun containerColor() = MaterialTheme.colorScheme.surface

    @Composable
    fun contentColor() = MaterialTheme.colorScheme.onSurface
}