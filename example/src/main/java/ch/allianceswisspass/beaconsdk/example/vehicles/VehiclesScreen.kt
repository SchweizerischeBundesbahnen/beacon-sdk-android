package ch.allianceswisspass.beaconsdk.example.vehicles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ch.allianceswisspass.beaconsdk.example.vehicles.ui.Body
import ch.allianceswisspass.beaconsdk.example.vehicles.ui.BottomBar
import ch.allianceswisspass.beaconsdk.example.vehicles.ui.TopBar

@Composable
fun VehiclesScreen() {
    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar()
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                Body()
            }
        },
    )
}
