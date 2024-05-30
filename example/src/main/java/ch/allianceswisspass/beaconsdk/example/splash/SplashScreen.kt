package ch.allianceswisspass.beaconsdk.example.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.Screen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun SplashScreen(navController: NavHostController) {
    Scaffold {
        Box(
            modifier = Modifier.padding(it),
        ) {
            PermissionsCheck {
                Body(
                    navController = navController,
                )
            }
        }
    }
}

@Composable
private fun Body(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFF0000))
    ) {
        AppIcon()
        LaunchedEffect(Unit) {
            delay(1.seconds)
            navController.navigate(route = Screen.Home.route) {
                popUpTo(route = Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
    }
}

@Composable
private fun BoxScope.AppIcon() {
    val painter = painterResource(id = R.drawable.ic_launcher_foreground)
    Image(
        painter = painter,
        contentDescription = "App icon",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(144.dp)
            .align(Alignment.Center),
    )
}
