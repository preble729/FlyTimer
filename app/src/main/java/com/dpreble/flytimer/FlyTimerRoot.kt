package com.dpreble.flytimer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dpreble.flytimer.athletes.AthleteDetailScreen
import com.dpreble.flytimer.athletes.AthletesScreen
import com.dpreble.flytimer.home.HomeScreen
import com.dpreble.flytimer.navigation.BottomNavigationBar
import com.dpreble.flytimer.settings.SettingsScreen
import com.dpreble.flytimer.timers.TimerScreen

@Composable
fun FlyTimerRoot() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen()
            }
            composable("athletes") {
                AthletesScreen(
                    onAthleteClick = { athleteId ->
                        navController.navigate("athletes/detail/$athleteId")
                    }
                )
            }
            composable(
                route = "athletes/detail/{athleteId}",
                arguments = listOf(
                    navArgument("athleteId") { type = NavType.LongType }
                )
            ) {
                AthleteDetailScreen()
            }
            composable("timers") {
                TimerScreen()
            }
            composable("settings") {
                SettingsScreen()
            }
        }
    }
}
