package ch.allianceswisspass.beaconsdk.example.vehicles.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.allianceswisspass.beaconsdk.example.widgets.LabeledText
import ch.allianceswisspass.beaconsdk.resolver.BeaconInfo
import ch.allianceswisspass.beaconsdk.resolver.Vehicle

@Composable
fun VehicleCard(
    modifier: Modifier = Modifier,
    vehicle: Vehicle,
    beaconInfos: List<BeaconInfo>,
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            TransportationType(vehicle = vehicle)
            Name(vehicle = vehicle)
            Uic(vehicle = vehicle)
            Decks(vehicle = vehicle)
            OrganizationCode(vehicle = vehicle)
            // Beacon infos
            beaconInfos.forEach { beaconInfo ->
                HorizontalDivider(modifier = Modifier.padding(top = 20.dp, bottom = 16.dp))
                Provider(beaconInfo = beaconInfo)
                ProviderBeaconId(beaconInfo = beaconInfo)
                Major(beaconInfo = beaconInfo)
                Minor(beaconInfo = beaconInfo)
                Deck(beaconInfo = beaconInfo)
                InstallationLocationName(beaconInfo = beaconInfo)
            }
        }
    }
}

@Composable
private fun TransportationType(vehicle: Vehicle) {
    LabeledText(
        label = "Transportation type",
        text = vehicle.transportationType,
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun Name(vehicle: Vehicle) {
    val name = vehicle.name
    when {
        name.isNullOrBlank() -> return
        else -> LabeledText(
            label = "Name",
            text = name,
            padding = PaddingValues(bottom = 4.dp),
        )
    }
}

@Composable
private fun Uic(vehicle: Vehicle) {
    val uic = vehicle.uic
    when {
        uic == null -> return
        else -> LabeledText(
            label = "UIC",
            text = uic.toString(),
            padding = PaddingValues(bottom = 4.dp),
        )
    }
}

@Composable
private fun Decks(vehicle: Vehicle) {
    LabeledText(
        label = "Decks",
        text = vehicle.decks.toString(),
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun OrganizationCode(vehicle: Vehicle) {
    LabeledText(
        label = "Organization",
        text = vehicle.organizationCode.toString(),
    )
}

@Composable
private fun Provider(beaconInfo: BeaconInfo) {
    val provider = beaconInfo.provider
    when {
        provider.isNullOrBlank() -> return
        else -> LabeledText(
            label = "Provider",
            text = provider,
            padding = PaddingValues(bottom = 4.dp),
        )
    }
}

@Composable
private fun ProviderBeaconId(beaconInfo: BeaconInfo) {
    val providerBeaconId = beaconInfo.providerBeaconId
    when {
        providerBeaconId.isNullOrBlank() -> return
        else -> LabeledText(
            label = "Beacon ID",
            text = providerBeaconId,
            padding = PaddingValues(bottom = 4.dp),
        )
    }
}

@Composable
private fun Major(beaconInfo: BeaconInfo) {
    LabeledText(
        label = "Major",
        text = beaconInfo.major.toString(),
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun Minor(beaconInfo: BeaconInfo) {
    LabeledText(
        label = "Minor",
        text = beaconInfo.minor.toString(),
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun Deck(beaconInfo: BeaconInfo) {
    LabeledText(
        label = "Deck",
        text = "${beaconInfo.installationLocation.deck} (${beaconInfo.installationLocation.deckName})",
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun InstallationLocationName(beaconInfo: BeaconInfo) {
    val name = beaconInfo.installationLocation.name
    when {
        name.isNullOrBlank() -> return
        else -> LabeledText(
            label = "Installation location",
            text = name,
        )
    }
}
