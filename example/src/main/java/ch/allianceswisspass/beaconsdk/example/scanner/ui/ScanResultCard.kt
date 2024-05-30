package ch.allianceswisspass.beaconsdk.example.scanner.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ch.allianceswisspass.beaconsdk.example.Format
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import java.util.*

@Composable
fun ScanResultCard(
    modifier: Modifier = Modifier,
    scanResult: ScanResult,
    body: @Composable ColumnScope.() -> Unit,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            Row {
                DeviceMacAddress(scanResult.deviceIdentifier)
                Timestamp(scanResult.timestamp)
            }
            Row {
                DeviceName(scanResult.deviceName)
                Rssi(scanResult.rssi)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            body.invoke(this)
        }
    }
}

@Composable
private fun RowScope.DeviceMacAddress(value: String) {
    Text(
        text = value,
        fontWeight = FontWeight.Light,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
    )
}

@Composable
private fun Timestamp(value: Date) {
    Text(
        text = Format.time(value, showSeconds = true),
        fontWeight = FontWeight.Light,
        style = MaterialTheme.typography.labelSmall,
    )
}

@Composable
private fun RowScope.DeviceName(value: String) {
    Text(
        text = when {
            value.isBlank() -> "-"
            else -> value
        },
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
    )
}

@Composable
private fun Rssi(value: Int) {
    Text(
        text = Format.rssi(value),
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.bodyLarge,
    )
}
