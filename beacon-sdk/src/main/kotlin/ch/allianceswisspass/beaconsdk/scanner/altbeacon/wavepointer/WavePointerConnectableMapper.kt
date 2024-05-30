package ch.allianceswisspass.beaconsdk.scanner.altbeacon.wavepointer

import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import ch.allianceswisspass.beaconsdk.scanner.WavePointerConnectable
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.BeaconMapper
import org.altbeacon.beacon.Beacon
import java.util.*

class WavePointerConnectableMapper : BeaconMapper {
    override fun map(beacon: Beacon): ScanResult? {
        return when {
            beacon.isMappable -> beacon.toScanResult()
            else -> null
        }
    }
}

// Extensions

private val Beacon.isMappable: Boolean
    get() {
        return parserIdentifier == BeaconType.WavePointerConnectable.name
    }


private fun Beacon.toScanResult(): ScanResult {
    return ScanResult(
        timestamp = Date(lastCycleDetectionTimestamp),
        deviceName = bluetoothName ?: "",
        deviceIdentifier = bluetoothAddress,
        rssi = rssi,
        distance = 0.0,
        beacon = toWavePointerConnectable(),
    )
}

private fun Beacon.toWavePointerConnectable(): WavePointerConnectable {
    return WavePointerConnectable(
        uuid = uuid,
        major = major,
        minor = minor,
        serialNumber = serialNumber,
        batteryVoltage = batteryVoltage,
        firmwareVersion = firmwareVersion,
        configurationVersion = configurationVersion,
    )
}

private val Beacon.uuid: String
    get() {
        val identifier = getIdentifier(0)
        return identifier.toUuid().toString()
    }

private val Beacon.major: Int
    get() {
        val identifier = getIdentifier(1)
        return identifier.toInt()
    }

private val Beacon.minor: Int
    get() {
        val identifier = getIdentifier(2)
        return identifier.toInt()
    }

private val Beacon.serialNumber: String
    get() {
        val identifier = getIdentifier(3)
        return identifier.toHexString().substring(3).uppercase()
    }

private val Beacon.batteryVoltage: Int
    get() {
        return txPower
    }

private val Beacon.firmwareVersion: UByte
    get() {
        val identifier = getIdentifier(4)
        val bytes = identifier.toByteArray()
        return bytes.first().toUByte()
    }

private val Beacon.configurationVersion: UByte
    get() {
        val identifier = getIdentifier(5)
        val bytes = identifier.toByteArray()
        return bytes.first().toUByte()
    }
