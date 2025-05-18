package com.wcp.tmdbcmp.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wcp.tmdbcmp.presentation.designsystem.theme.AppPreviewWrapper
import com.wcp.tmdbcmp.presentation.designsystem.theme.ThemePreviews
import com.wcp.tmdbcmp.presentation.discover.ui.Discover
import com.wcp.tmdbcmp.presentation.discover.ui.SurfaceContainerAlpha
import com.wcp.tmdbcmp.presentation.favorites.Favorites
import com.wcp.tmdbcmp.presentation.settings.Settings
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import tmdb_compose_multiplatform.composeapp.generated.resources.Res
import tmdb_compose_multiplatform.composeapp.generated.resources.ic_discover
import tmdb_compose_multiplatform.composeapp.generated.resources.ic_favorite_off
import tmdb_compose_multiplatform.composeapp.generated.resources.ic_settings
import kotlin.reflect.KClass

enum class DefaultBottomBarNavigationItem(
    val route: Any,
    val label: String,
    val icon: DrawableResource,
) {
    DiscoverItem(
        Discover,
        "Discover",
        Res.drawable.ic_discover,
    ),

    FavoritesItem(
        Favorites,
        "Favorites",
        Res.drawable.ic_favorite_off,
    ),
    SettingsItem(
        Settings,
        "Settings",
        Res.drawable.ic_settings,
    ),
}

private fun NavBackStackEntry?.isRouteSelected(route: KClass<*>): Boolean = this?.destination?.hasRoute(route) == true

@Composable
fun DefaultBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val bottomBarPadding =
        WindowInsets.navigationBars
            .asPaddingValues(LocalDensity.current)
            .calculateBottomPadding()

    NavigationBar(
        modifier = modifier,
        windowInsets = WindowInsets(bottom = bottomBarPadding),
        containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = SurfaceContainerAlpha),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        DefaultBottomBarNavigationItem.entries
            .forEach { item ->
                NavigationBarItem(
                    selected = navBackStackEntry.isRouteSelected(item.route::class),
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.name,
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                        )
                    },
                )
            }
    }
}

@ThemePreviews
@Composable
private fun BottomNavigationPreview() {
    AppPreviewWrapper {
        DefaultBottomNavigation(
            navController = rememberNavController(),
        )
    }
}
