package com.dpreble.flytimer.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

private data class BottomDestination(
    val route: String,
    val label: String
)

private val bottomDestinations = listOf(
    BottomDestination(route = "home", label = "Home"),
    BottomDestination(route = "athletes", label = "Athletes"),
    BottomDestination(route = "timers", label = "Timers"),
    BottomDestination(route = "settings", label = "Settings")
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        bottomDestinations.forEach { destination ->
            val currentRoute = currentDestination?.route.orEmpty()
            val selected = currentDestination
                ?.hierarchy
                ?.any {
                    it.route == destination.route ||
                        (destination.route == "athletes" && currentRoute.startsWith("athletes/"))
                } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Text(destination.label.take(1)) },
                label = { Text(destination.label) }
            )
        }
    }
}
