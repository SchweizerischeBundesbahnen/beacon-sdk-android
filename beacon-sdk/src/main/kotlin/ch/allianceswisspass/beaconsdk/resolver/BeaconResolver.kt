package ch.allianceswisspass.beaconsdk.resolver

import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import kotlinx.coroutines.flow.Flow

/**
 * A BeaconResolver provides methods to get information about scanned beacons.
 */
interface BeaconResolver {

    /**
     * Get beacon information for the given sequence of scan results.
     *
     * Only scan results with Eddystone EID or iBeacon beacon are taken into account. Scan results with other beacon
     * types are ignored.
     *
     * @param scanResults A flow which emits the list of scan results that should be resolved.
     *
     * @return A flow which emits a map which contains beacon information for each scan result or null for scan results
     * where no beacon information is available.
     */
    fun resolve(scanResults: Flow<List<ScanResult>>): Flow<Map<ScanResult, BeaconInfo>>

    /**
     * Get beacon information for the given scan results.
     *
     * Only scan results with Eddystone EID or iBeacon beacon are taken into account. Scan results with other beacon
     * types are ignored.
     *
     * @param scanResults The list of scan results that should be resolved.
     *
     * @return A map which contains beacon information for each scan result or null for scan results where no beacon
     * information is available.
     */
    suspend fun resolve(scanResults: List<ScanResult>): Map<ScanResult, BeaconInfo>
}
