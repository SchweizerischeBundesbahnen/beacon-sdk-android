package ch.allianceswisspass.beaconsdk.scanner.altbeacon.ibeacon

import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import org.altbeacon.beacon.BeaconParser

private const val BEACON_LAYOUT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
private const val MANUFACTURER = 0x004c

internal class IBeaconParser : BeaconParser(BeaconType.IBeacon.name) {
    init {
        setBeaconLayout(BEACON_LAYOUT)
        setHardwareAssistManufacturerCodes(intArrayOf(MANUFACTURER))
    }
}
