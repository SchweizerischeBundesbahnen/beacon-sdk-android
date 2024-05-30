package ch.allianceswisspass.beaconsdk.scanner

import java.util.*

/**
 * Scan result
 *
 * @property timestamp The timestamp when this scan result was observed.
 * @property deviceName The name of the bluetooth device.
 * @property deviceIdentifier The identifier of the bluetooth device.
 * @property rssi The received signal strength in dBm.
 * @property distance The estimated distance to the beacon in meters.
 * @property beacon The scanned beacon.
 */
data class ScanResult(
    val timestamp: Date,
    val deviceName: String,
    val deviceIdentifier: String,
    val rssi: Int,
    val distance: Double,
    val beacon: Beacon,
)
