package ch.allianceswisspass.beaconsdk.example.scanner.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.scanner.ScannerViewModel
import ch.allianceswisspass.beaconsdk.example.scanner.SectionState
import ch.allianceswisspass.beaconsdk.example.scanner.ui.eddystone.eid.EddystoneEidSection
import ch.allianceswisspass.beaconsdk.example.scanner.ui.eddystone.uic.EddystoneUicSection
import ch.allianceswisspass.beaconsdk.example.scanner.ui.ibeacon.IBeaconSection
import ch.allianceswisspass.beaconsdk.example.scanner.ui.kontakt.KontaktSecureProfileSection
import ch.allianceswisspass.beaconsdk.example.scanner.ui.wavepointer.WavePointerConnectableSection
import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.ScanResult

@Composable
fun Body() {
    val viewModel: ScannerViewModel = hiltViewModel()
    val scanResults = viewModel.scanResults.observeAsState(initial = emptyList()).value
    val sectionStates = viewModel.sectionStates.observeAsState(initial = emptyMap()).value
    val isScanning = viewModel.isScanning.observeAsState(initial = false).value
    when {
        scanResults.isNotEmpty() -> ScanResults(
            scanResults = scanResults.groupBy { it.beacon.type },
            sectionStates = sectionStates,
            onUpdateSectionState = { beaconType, state ->
                viewModel.updateSectionState(beaconType, state)
            },
        )

        isScanning -> Scanning()
        else -> Idle()
    }
}

@Composable
private fun ScanResults(
    scanResults: Map<BeaconType, List<ScanResult>>,
    sectionStates: Map<BeaconType, SectionState>,
    onUpdateSectionState: (beaconType: BeaconType, state: SectionState) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
    ) {
        BeaconType.EddystoneEid.let { beaconType ->
            EddystoneEidSection(
                scanResults = scanResults[beaconType] ?: emptyList(),
                sectionState = sectionStates[beaconType] ?: SectionState.Expanded,
                onUpdateSectionState = { state ->
                    onUpdateSectionState(beaconType, state)
                },
            )
        }
        BeaconType.EddystoneUic.let { beaconType ->
            EddystoneUicSection(
                scanResults = scanResults[beaconType] ?: emptyList(),
                sectionState = sectionStates[beaconType] ?: SectionState.Expanded,
                onUpdateSectionState = { state ->
                    onUpdateSectionState(beaconType, state)
                },
            )
        }
        BeaconType.IBeacon.let { beaconType ->
            IBeaconSection(
                scanResults = scanResults[beaconType] ?: emptyList(),
                sectionState = sectionStates[beaconType] ?: SectionState.Expanded,
                onUpdateSectionState = { state ->
                    onUpdateSectionState(beaconType, state)
                },
            )
        }
        BeaconType.KontaktSecureProfile.let { beaconType ->
            KontaktSecureProfileSection(
                scanResults = scanResults[beaconType] ?: emptyList(),
                sectionState = sectionStates[beaconType] ?: SectionState.Expanded,
                onUpdateSectionState = { state ->
                    onUpdateSectionState(beaconType, state)
                },
            )
        }
        BeaconType.WavePointerConnectable.let { beaconType ->
            WavePointerConnectableSection(
                scanResults = scanResults[beaconType] ?: emptyList(),
                sectionState = sectionStates[beaconType] ?: SectionState.Expanded,
                onUpdateSectionState = { state ->
                    onUpdateSectionState(beaconType, state)
                },
            )
        }
    }
}

@Composable
private fun Scanning() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(64.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(bottom = 16.dp),
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(72.dp),
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_bluetooth),
                contentDescription = null,
                modifier = Modifier.size(56.dp),
            )
        }
        Text(
            text = stringResource(id = R.string.scanning),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun Idle() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(64.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_bluetooth),
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .padding(bottom = 16.dp),
        )
        Text(
            text = stringResource(id = R.string.start_scanning_hint),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
