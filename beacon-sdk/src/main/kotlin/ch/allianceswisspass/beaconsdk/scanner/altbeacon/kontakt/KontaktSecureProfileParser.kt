package ch.allianceswisspass.beaconsdk.scanner.altbeacon.kontakt

import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import org.altbeacon.beacon.BeaconParser

private const val BEACON_LAYOUT = "s:0-1=fe6a,m:2-2=02,p:7-7,i:8-15v,d:3-3,d:4-4,d:5-5,d:6-6"
private const val MANUFACTURER = 0xfe6a

class KontaktSecureProfileParser : BeaconParser(BeaconType.KontaktSecureProfile.name) {
    init {
        setBeaconLayout(BEACON_LAYOUT)
        setHardwareAssistManufacturerCodes(intArrayOf(MANUFACTURER))
    }
}
