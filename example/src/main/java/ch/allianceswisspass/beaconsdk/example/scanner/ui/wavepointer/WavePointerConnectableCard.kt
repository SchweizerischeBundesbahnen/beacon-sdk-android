package ch.allianceswisspass.beaconsdk.example.scanner.ui.wavepointer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.allianceswisspass.beaconsdk.example.Format
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.scanner.ui.ScanResultCard
import ch.allianceswisspass.beaconsdk.example.widgets.LabeledText
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import ch.allianceswisspass.beaconsdk.scanner.WavePointerConnectable
import java.util.*

@Composable
fun WavePointerConnectableCard(scanResult: ScanResult) {
    val beacon = scanResult.beacon as WavePointerConnectable
    ScanResultCard(scanResult = scanResult) {
        SerialNumber(beacon = beacon)
        FirmwareVersion(beacon = beacon)
        ConfigurationVersion(beacon = beacon)
        BatteryVoltage(beacon = beacon)
    }
}

@Composable
private fun SerialNumber(beacon: WavePointerConnectable) {
    LabeledText(
        label = stringResource(id = R.string.serial_number),
        text = beacon.serialNumber,
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun FirmwareVersion(beacon: WavePointerConnectable) {
    LabeledText(
        label = stringResource(id = R.string.firmware_version),
        text = beacon.firmwareVersion.toString(),
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun ConfigurationVersion(beacon: WavePointerConnectable) {
    LabeledText(
        label = stringResource(id = R.string.configuration_version),
        text = beacon.configurationVersion.toString(),
        padding = PaddingValues(bottom = 4.dp),
    )
}

@Composable
private fun BatteryVoltage(beacon: WavePointerConnectable) {
    LabeledText(
        label = stringResource(id = R.string.battery_voltage),
        text = Format.milliVolt(beacon.batteryVoltage),
    )
}

// Preview

@Preview
@Composable
private fun WavePointerConnectableCardPreview() {
    WavePointerConnectableCard(
        scanResult = ScanResult(
            timestamp = Date(),
            deviceName = "WP003031",
            deviceIdentifier = "FF:FF:FF:FF:FF:FF",
            rssi = -12,
            distance = 1.735,
            beacon = WavePointerConnectable(
                uuid = "aea3e301-4bbc-4ecf-ad17-2573922a5f4f",
                major = 60123,
                minor = 54,
                serialNumber = "E23140000003031",
                batteryVoltage = 3598,
                firmwareVersion = 15u,
                configurationVersion = 3u,
            ),
        ),
    )
}