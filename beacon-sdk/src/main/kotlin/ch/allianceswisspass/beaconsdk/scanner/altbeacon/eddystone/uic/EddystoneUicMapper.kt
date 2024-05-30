package ch.allianceswisspass.beaconsdk.scanner.altbeacon.eddystone.uic

import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.EddystoneUic
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.BeaconMapper
import org.altbeacon.beacon.Beacon
import java.util.*

class EddystoneUicMapper : BeaconMapper {
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
        return parserIdentifier == BeaconType.EddystoneUic.name && byteArrayOf(0x00).contentEquals(padding)
    }

private fun Beacon.toScanResult(): ScanResult {
    return ScanResult(
        timestamp = Date(lastCycleDetectionTimestamp),
        deviceName = bluetoothName ?: "",
        deviceIdentifier = bluetoothAddress,
        rssi = rssi,
        distance = distance,
        beacon = toEddystoneUic(),
    )
}

private fun Beacon.toEddystoneUic(): EddystoneUic {
    return EddystoneUic(
        txPower = txPower + 41,
        uic = uic,
        installationLocationName = installationLocationName,
    )
}

private val Beacon.padding: ByteArray
    get() {
        val identifier = getIdentifier(0)
        return identifier.toByteArray()
    }

private val Beacon.uic: String
    get() {
        val identifier = getIdentifier(1)
        return identifier.toByteArray().decodeToString()
    }

private val Beacon.installationLocationName: String
    get() {
        val identifier = getIdentifier(2)
        return identifier.toByteArray().decodeToString()
    }
