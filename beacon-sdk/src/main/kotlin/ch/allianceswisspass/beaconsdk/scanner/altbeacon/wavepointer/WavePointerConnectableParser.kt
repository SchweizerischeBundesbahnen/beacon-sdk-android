package ch.allianceswisspass.beaconsdk.scanner.altbeacon.wavepointer

import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import org.altbeacon.beacon.BeaconParser

private const val BEACON_LAYOUT = "m:0-3=2004beac,i:4-19,i:20-21,i:22-23,p:24-25,i:32-39,i:40-40,i:41-41"
private const val MANUFACTURER = 0x2004

class WavePointerConnectableParser : BeaconParser(BeaconType.WavePointerConnectable.name) {
    init {
        setBeaconLayout(BEACON_LAYOUT)
        setHardwareAssistManufacturerCodes(intArrayOf(MANUFACTURER))
    }
}
