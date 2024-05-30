package ch.allianceswisspass.beaconsdk.example.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.widgets.AspTopAppBar

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { AppBar() },
    ) {
        Box(modifier = Modifier.padding(it)) {
            Body(navController = navController)
        }
    }
}

@Composable
private fun AppBar() {
    AspTopAppBar(
        title = stringResource(id = R.string.home_title),
    )
}

@Composable
private fun Body(navController: NavHostController) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
        ) {
            HomeMenu(navController = navController)
        }
        AppVersion()
    }
}
