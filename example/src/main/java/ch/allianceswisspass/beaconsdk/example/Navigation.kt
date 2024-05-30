package ch.allianceswisspass.beaconsdk.example

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ch.allianceswisspass.beaconsdk.example.home.HomeScreen
import ch.allianceswisspass.beaconsdk.example.scanner.ScannerScreen
import ch.allianceswisspass.beaconsdk.example.splash.SplashScreen
import ch.allianceswisspass.beaconsdk.example.vehicles.VehiclesScreen

@Composable
fun MainNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        enterTransition = {
            fadeIn(
                animationSpec = tween(durationMillis = 200),
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(durationMillis = 200),
            )
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Scanner.route) {
            ScannerScreen()
        }
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Vehicles.route) {
            VehiclesScreen()
        }
    }
}
