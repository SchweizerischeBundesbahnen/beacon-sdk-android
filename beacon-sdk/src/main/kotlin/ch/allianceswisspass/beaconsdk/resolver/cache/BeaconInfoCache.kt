package ch.allianceswisspass.beaconsdk.resolver.cache

import ch.allianceswisspass.beaconsdk.resolver.BeaconInfo
import ch.allianceswisspass.beaconsdk.scanner.Beacon
import java.util.*


interface BeaconInfoCache {
    fun add(beacon: Beacon, beaconInfo: BeaconInfo?)
    fun get(beacon: Beacon): Entry?

    data class Entry(
        val timestamp: Date,
        val beaconInfo: BeaconInfo?,
    )
}
