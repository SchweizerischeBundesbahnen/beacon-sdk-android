package ch.allianceswisspass.beaconsdk.example.scanner.ui.ibeacon


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.allianceswisspass.beaconsdk.example.Format
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.scanner.ui.ScanResultCard
import ch.allianceswisspass.beaconsdk.example.widgets.LabeledText
import ch.allianceswisspass.beaconsdk.scanner.IBeacon
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import java.util.*

@Composable
fun IBeaconCard(scanResult: ScanResult) {
    val beacon = scanResult.beacon as IBeacon
    ScanResultCard(scanResult = scanResult) {
        ProximityUuid(beacon = beacon)
        Major(beacon = beacon)
        Minor(beacon = beacon)
        TxPower(beacon = beacon)
        Distance(scanResult = scanResult)
    }
}

@Composable
private fun ProximityUuid(beacon: IBeacon) {
    LabeledText(
        label = stringResource(id = R.string.proximity_uuid),
        text = beacon.proximityUuid,
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun Major(beacon: IBeacon) {
    LabeledText(
        label = stringResource(id = R.string.major),
        text = beacon.major.toString(),
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun Minor(beacon: IBeacon) {
    LabeledText(
        label = stringResource(id = R.string.minor),
        text = beacon.minor.toString(),
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun TxPower(beacon: IBeacon) {
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
private fun IBeaconCardPreview() {
    IBeaconCard(
        scanResult = ScanResult(
            timestamp = Date(),
            deviceName = "",
            deviceIdentifier = "FF:FF:FF:FF:FF:FF",
            rssi = -12,
            distance = 1.735,
            beacon = IBeacon(
                proximityUuid = "aea3e301-4bbc-4ecf-ad17-2573922a5f4f",
                major = 60123,
                minor = 54,
                txPower = 0,
            ),
        ),
    )
}
