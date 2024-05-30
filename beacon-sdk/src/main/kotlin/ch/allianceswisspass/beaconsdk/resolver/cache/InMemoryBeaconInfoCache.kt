package ch.allianceswisspass.beaconsdk.resolver.cache

import ch.allianceswisspass.beaconsdk.resolver.BeaconInfo
import ch.allianceswisspass.beaconsdk.scanner.Beacon
import java.util.*

class InMemoryBeaconInfoCache : BeaconInfoCache {

    private val data = mutableMapOf<Beacon, BeaconInfoCache.Entry>()

    override fun add(beacon: Beacon, beaconInfo: BeaconInfo?) {
        data[beacon] = BeaconInfoCache.Entry(
            timestamp = Date(),
            beaconInfo = beaconInfo,
        )
    }

    override fun get(beacon: Beacon): BeaconInfoCache.Entry? {
        return data[beacon]
    }
}
