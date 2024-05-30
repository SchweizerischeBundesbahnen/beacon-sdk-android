package ch.allianceswisspass.beaconsdk.example.scanner.ui.eddystone.eid

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.allianceswisspass.beaconsdk.example.Format
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.scanner.ui.ScanResultCard
import ch.allianceswisspass.beaconsdk.example.widgets.LabeledText
import ch.allianceswisspass.beaconsdk.scanner.EddystoneEid
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import java.util.*

@Composable
fun EddystoneEidCard(scanResult: ScanResult) {
    val beacon = scanResult.beacon as EddystoneEid
    ScanResultCard(scanResult = scanResult) {
        Eid(beacon = beacon)
        TxPower(beacon = beacon)
        Distance(scanResult = scanResult)
    }
}

@Composable
private fun Eid(beacon: EddystoneEid) {
    LabeledText(
        label = stringResource(id = R.string.eid),
        text = beacon.eid,
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun TxPower(beacon: EddystoneEid) {
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
private fun EddystoneEidCardPreview() {
    EddystoneEidCard(
        scanResult = ScanResult(
            timestamp = Date(),
            deviceName = "",
            deviceIdentifier = "FF:FF:FF:FF:FF:FF",
            rssi = -12,
            distance = 1.735,
            beacon = EddystoneEid(
                txPower = -66,
                eid = "c22fe1f198109681",
            ),
        ),
    )
}
