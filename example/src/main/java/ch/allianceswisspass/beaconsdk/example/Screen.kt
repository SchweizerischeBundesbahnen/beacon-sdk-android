package ch.allianceswisspass.beaconsdk.example

sealed class Screen(val route: String) {
    data object Splash : Screen(route = "splash")
    data object Home : Screen(route = "home")
    data object Scanner : Screen(route = "scanner")
    data object Vehicles : Screen(route = "vehicles")
}
