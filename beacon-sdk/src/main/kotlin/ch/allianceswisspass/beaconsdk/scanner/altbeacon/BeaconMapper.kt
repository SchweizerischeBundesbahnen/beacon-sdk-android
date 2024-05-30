package ch.allianceswisspass.beaconsdk.scanner.altbeacon

import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import org.altbeacon.beacon.Beacon

internal interface BeaconMapper {
    fun map(beacon: Beacon): ScanResult?
}
