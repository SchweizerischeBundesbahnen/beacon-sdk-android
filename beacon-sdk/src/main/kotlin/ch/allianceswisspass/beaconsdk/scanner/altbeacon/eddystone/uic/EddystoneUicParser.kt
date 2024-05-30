package ch.allianceswisspass.beaconsdk.scanner.altbeacon.eddystone.uic

import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import org.altbeacon.beacon.BeaconParser

private const val BEACON_LAYOUT = "s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-4,i:5-16,i:17-19"
private const val MANUFACTURER = 0xfeaa

class EddystoneUicParser : BeaconParser(BeaconType.EddystoneUic.name) {
    init {
        setBeaconLayout(BEACON_LAYOUT)
        setHardwareAssistManufacturerCodes(intArrayOf(MANUFACTURER))
    }
}
