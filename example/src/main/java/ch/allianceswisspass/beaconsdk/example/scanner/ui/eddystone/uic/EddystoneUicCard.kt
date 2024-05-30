package ch.allianceswisspass.beaconsdk.example.scanner.ui.eddystone.uic

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.allianceswisspass.beaconsdk.example.Format
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.scanner.ui.ScanResultCard
import ch.allianceswisspass.beaconsdk.example.widgets.LabeledText
import ch.allianceswisspass.beaconsdk.scanner.EddystoneUic
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import java.util.*

@Composable
fun EddystoneUicCard(scanResult: ScanResult) {
    val beacon = scanResult.beacon as EddystoneUic
    ScanResultCard(scanResult = scanResult) {
        Uic(beacon = beacon)
        InstallationLocationName(beacon = beacon)
        TxPower(beacon = beacon)
        Distance(scanResult = scanResult)
    }
}

// Private

@Composable
private fun Uic(beacon: EddystoneUic) {
    LabeledText(
        label = stringResource(id = R.string.uic),
        text = beacon.uic,
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun InstallationLocationName(beacon: EddystoneUic) {
    LabeledText(
        label = stringResource(id = R.string.installation_location),
        text = beacon.installationLocationName,
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun TxPower(beacon: EddystoneUic) {
    LabeledText(
        label = stringResource(id = R.string.tx_power),
        text = Format.txPower(beacon.txPower),
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun Distance(scanResult: ScanResult) {
    LabeledText(
        label = stringResource(id = R.string.distance),
        text = Format.meters(scanResult.distance),
    )
}

// Preview

@Preview
@Composable
private fun EddystoneUicCardPreview() {
    EddystoneUicCard(
        scanResult = ScanResult(
            timestamp = Date(),
            deviceName = "",
            deviceIdentifier = "FF:FF:FF:FF:FF:FF",
            rssi = -12,
            distance = 1.735,
            beacon = EddystoneUic(
                txPower = -66,
                uic = "508516940063",
                installationLocationName = "D35",
            ),
        ),
    )
}