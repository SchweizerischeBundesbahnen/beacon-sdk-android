package ch.allianceswisspass.beaconsdk.scanner

import kotlinx.coroutines.flow.Flow

/**
 * Provides methods to scan for beacons.
 */
interface BeaconScanner {

    /**
     * A flow which emits the scan results.
     */
    val scanResults: Flow<List<ScanResult>>

    /**
     * Starts scanning.
     */
    fun start()

    /**
     * Stops scanning.
     */
    fun stop()
}
