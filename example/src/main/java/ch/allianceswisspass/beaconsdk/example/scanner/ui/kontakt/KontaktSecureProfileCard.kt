package ch.allianceswisspass.beaconsdk.example.scanner.ui.kontakt

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.allianceswisspass.beaconsdk.example.Format
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.scanner.ui.ScanResultCard
import ch.allianceswisspass.beaconsdk.example.widgets.LabeledText
import ch.allianceswisspass.beaconsdk.scanner.KontaktSecureProfile
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import java.util.*

@Composable
fun KontaktSecureProfileCard(scanResult: ScanResult) {
    val beacon = scanResult.beacon as KontaktSecureProfile
    ScanResultCard(scanResult = scanResult) {
        UniqueId(beacon = beacon)
        DeviceModel(beacon = beacon)
        FirmwareVersion(beacon = beacon)
        BatteryLevel(beacon = beacon)
        TxPower(beacon = beacon)
    }
}

// Private

@Composable
private fun UniqueId(beacon: KontaktSecureProfile) {
    LabeledText(
        label = stringResource(id = R.string.unique_id),
        text = beacon.uniqueId,
        padding = PaddingValues(bottom = 4.dp),
    )
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
private fun DeviceModel(beacon: KontaktSecureProfile) {
    LabeledText(
        label = stringResource(id = R.string.device_model),
        text = "0x${beacon.deviceModel.toHexString()}",
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun FirmwareVersion(beacon: KontaktSecureProfile) {
    LabeledText(
        label = stringResource(id = R.string.firmware_version),
        text = beacon.firmwareVersion.joinToString(separator = "."),
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun BatteryLevel(beacon: KontaktSecureProfile) {
    LabeledText(
        label = stringResource(id = R.string.battery_level),
        text = Format.percent(beacon.batteryLevel),
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun TxPower(beacon: KontaktSecureProfile) {
    LabeledText(
        label = stringResource(id = R.string.tx_power),
        text = Format.txPower(beacon.txPower),
    )
}

// Preview

@Preview
@Composable
private fun KontaktSecureProfileCardPreview() {
    KontaktSecureProfileCard(
        scanResult = ScanResult(
            timestamp = Date(),
            deviceName = "508516940063D66",
            deviceIdentifier = "FF:FF:FF:FF:FF:FF",
            rssi = -12,
            distance = 1.735,
            beacon = KontaktSecureProfile(
                uniqueId = "fontm1a3",
                deviceModel = 6,
                firmwareVersion = listOf(2, 0),
                batteryLevel = 99,
                txPower = -12,
            ),
        ),
    )
}