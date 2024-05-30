package ch.allianceswisspass.beaconsdk.example.scanner.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.scanner.ScannerViewModel

@Composable
fun BottomBar() {
    val viewModel: ScannerViewModel = hiltViewModel()
    val isScanning = viewModel.isScanning.observeAsState(initial = false).value
    BottomAppBar(
        tonalElevation = 0.dp,
    ) {
        when {
            isScanning -> StopScanningButton(viewModel)
            else -> StartScanningButton(viewModel)
        }
    }
}

@Composable
private fun StartScanningButton(viewModel: ScannerViewModel) {
    Button(
        onClick = {
            viewModel.startScanning()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.start_scanning)
        )
    }
}

@Composable
private fun StopScanningButton(viewModel: ScannerViewModel) {
    Button(
        onClick = {
            viewModel.stopScanning()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.stop_scanning)
        )
    }
}
