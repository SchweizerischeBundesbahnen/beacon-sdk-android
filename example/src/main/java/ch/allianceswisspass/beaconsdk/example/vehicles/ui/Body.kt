package ch.allianceswisspass.beaconsdk.example.vehicles.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import ch.allianceswisspass.beaconsdk.example.vehicles.VehiclesViewModel
import ch.allianceswisspass.beaconsdk.resolver.BeaconInfo
import ch.allianceswisspass.beaconsdk.resolver.Vehicle

@Composable
fun Body() {
    val viewModel: VehiclesViewModel = hiltViewModel()
    val vehicles = viewModel.vehicles.observeAsState(initial = emptyMap()).value
    val isScanning = viewModel.isScanning.observeAsState(initial = false).value
    when {
        vehicles.isNotEmpty() -> Vehicles(vehicles = vehicles)
        isScanning -> Scanning()
        else -> Idle()
    }
}

@Composable
private fun Vehicles(vehicles: Map<Vehicle, List<BeaconInfo>>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 16.dp),
    ) {
        val keys = vehicles.keys.sortedBy { it.name }.sortedBy { it.uic }
        items(keys) { vehicle ->
            val beaconInfos = vehicles[vehicle]!! //
                .sortedBy { beaconInfo -> beaconInfo.providerBeaconId } //
                .sortedBy { beaconInfo -> beaconInfo.provider } //
                .sortedBy { beaconInfo -> beaconInfo.installationLocation.deck }
            VehicleCard(
                vehicle = vehicle,
                beaconInfos = beaconInfos,
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
                painter = painterResource(id = R.drawable.ic_train_bus),
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
            painter = painterResource(id = R.drawable.ic_train_bus),
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .padding(bottom = 16.dp),
        )
        Text(
            text = stringResource(id = R.string.start_scanning_hint_vehicles),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
