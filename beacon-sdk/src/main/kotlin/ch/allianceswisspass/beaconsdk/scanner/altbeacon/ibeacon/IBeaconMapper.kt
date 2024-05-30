package ch.allianceswisspass.beaconsdk.scanner.altbeacon.ibeacon

import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.IBeacon
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.BeaconMapper
import org.altbeacon.beacon.Beacon
import java.util.*

class IBeaconMapper : BeaconMapper {
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
        return parserIdentifier == BeaconType.IBeacon.name && proximityUuid == "aea3e301-4bbc-4ecf-ad17-2573922a5f4f"
    }

private fun Beacon.toScanResult(): ScanResult {
    return ScanResult(
        timestamp = Date(lastCycleDetectionTimestamp),
        deviceName = bluetoothName ?: "",
        deviceIdentifier = bluetoothAddress,
        rssi = rssi,
        distance = distance,
        beacon = toIBeacon(),
    )
}

private fun Beacon.toIBeacon(): IBeacon {
    return IBeacon(
        proximityUuid = proximityUuid,
        major = major,
        minor = minor,
        txPower = txPower,
    )
}

private val Beacon.proximityUuid: String
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