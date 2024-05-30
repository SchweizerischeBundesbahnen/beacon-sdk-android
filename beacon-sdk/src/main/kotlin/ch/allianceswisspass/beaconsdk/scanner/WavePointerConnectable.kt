package ch.allianceswisspass.beaconsdk.scanner

/**
 * WavePointerConnectable
 *
 * @property uuid A unique ID that identifies a beacon or a set of beacons as being of a certain type or from a certain
 * organization.
 * @property major A major identifier, to differentiate this beacon from other beacons with the same UUID.
 * @property minor A minor identifier, to differentiate this beacon from other beacons with the same UUID and major
 * identifier.
 * @property serialNumber The serial number of the beacon.
 * @property batteryVoltage The voltage of the battery in milli volt (`mV`).
 * @property firmwareVersion The version of the installed firmware.
 * @property configurationVersion The version of the current configuration.
 */
data class WavePointerConnectable(
    val uuid: String,
    val major: Int,
    val minor: Int,
    val serialNumber: String,
    val batteryVoltage: Int,
    val firmwareVersion: UByte,
    val configurationVersion: UByte,
) : Beacon(
    type = BeaconType.WavePointerConnectable,
)
