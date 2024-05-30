package ch.allianceswisspass.beaconsdk.example.scanner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ch.allianceswisspass.beaconsdk.example.scanner.ui.Body
import ch.allianceswisspass.beaconsdk.example.scanner.ui.BottomBar
import ch.allianceswisspass.beaconsdk.example.scanner.ui.TopBar

@Composable
fun ScannerScreen() {
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
        }
    )
}
