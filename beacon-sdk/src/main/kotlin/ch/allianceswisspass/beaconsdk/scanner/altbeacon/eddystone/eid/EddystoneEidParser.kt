package ch.allianceswisspass.beaconsdk.scanner.altbeacon.eddystone.eid

import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import org.altbeacon.beacon.BeaconParser

private const val BEACON_LAYOUT = "s:0-1=feaa,m:2-2=30,p:3-3:-41,i:4-11"
private const val MANUFACTURER = 0xfeaa

internal class EddystoneEidParser : BeaconParser(BeaconType.EddystoneEid.name) {
    init {
        setBeaconLayout(BEACON_LAYOUT)
        setHardwareAssistManufacturerCodes(intArrayOf(MANUFACTURER))
    }
}
