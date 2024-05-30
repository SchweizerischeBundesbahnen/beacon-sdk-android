package ch.allianceswisspass.beaconsdk.scanner.altbeacon.eddystone.eid

import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.EddystoneEid
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.BeaconMapper
import org.altbeacon.beacon.Beacon
import java.util.*

internal class EddystoneEidMapper : BeaconMapper {
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
        return parserIdentifier == BeaconType.EddystoneEid.name
    }


private fun Beacon.toScanResult(): ScanResult {
    return ScanResult(
        timestamp = Date(lastCycleDetectionTimestamp),
        deviceName = bluetoothName ?: "",
        deviceIdentifier = bluetoothAddress,
        rssi = rssi,
        distance = distance,
        beacon = toEddystoneEid(),
    )
}

private fun Beacon.toEddystoneEid(): EddystoneEid {
    return EddystoneEid(
        txPower = txPower + 41,
        eid = eid,
    )
}

private val Beacon.eid: String
    get() {
        val identifier = getIdentifier(0)
        return identifier.toString().substring(2)
    }
