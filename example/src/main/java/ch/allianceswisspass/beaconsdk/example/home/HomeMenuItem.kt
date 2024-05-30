package ch.allianceswisspass.beaconsdk.example.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavHostController
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.Screen

enum class HomeMenuItem(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val onClick: (navController: NavHostController) -> Unit
) {
    Scanner(
        icon = R.drawable.ic_bluetooth,
        label = R.string.beacon_scanner,
        onClick = {
            it.navigate(route = Screen.Scanner.route)
        },
    ),
    Vehicles(
        icon = R.drawable.ic_train_bus,
        label = R.string.nearby_vehicles,
        onClick = {
            it.navigate(route = Screen.Vehicles.route)
        },
    ),
}
