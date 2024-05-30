package ch.allianceswisspass.beaconsdk.scanner.altbeacon.kontakt

import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.KontaktSecureProfile
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.BeaconMapper
import org.altbeacon.beacon.Beacon
import java.nio.charset.Charset
import java.util.*

class KontaktSecureProfileMapper : BeaconMapper {
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
        return parserIdentifier == BeaconType.KontaktSecureProfile.name
    }

private fun Beacon.toScanResult(): ScanResult {
    return ScanResult(
        timestamp = Date(lastCycleDetectionTimestamp),
        deviceName = bluetoothName ?: "",
        deviceIdentifier = bluetoothAddress,
        rssi = rssi,
        distance = 0.0,
        beacon = toKontaktSecureProfile(),
    )
}

private fun Beacon.toKontaktSecureProfile(): KontaktSecureProfile {
    return KontaktSecureProfile(
        uniqueId = uniqueId,
        deviceModel = deviceModel,
        firmwareVersion = firmwareVersion,
        batteryLevel = batteryLevel,
        txPower = txPower,
    )
}

private val Beacon.uniqueId: String
    get() {
        val identifier = getIdentifier(0)
        val bytes = identifier.toByteArray()
        return String(bytes, charset = Charset.forName("UTF-8"))
    }

private val Beacon.deviceModel: Byte
    get() {
        return dataFields[0].toByte()
    }

private val Beacon.firmwareVersion: List<Int>
    get() {
        val major = dataFields[1].toInt()
        val minor = dataFields[2].toInt()
        return listOf(major, minor)
    }

private val Beacon.batteryLevel: Int
    get() {
        return dataFields[3].toInt()
    }
