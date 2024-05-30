package ch.allianceswisspass.beaconsdk.resolver

import ch.allianceswisspass.beaconsdk.resolver.cache.BeaconInfoCache
import ch.allianceswisspass.beaconsdk.scanner.EddystoneEid
import ch.allianceswisspass.beaconsdk.scanner.IBeacon
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import ch.allianceswisspass.beaconsdk.usecaseapi.UseCaseApi
import ch.allianceswisspass.beaconsdk.usecaseapi.resolve.ResolveResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class BeaconResolverImpl(
    private val useCaseApi: UseCaseApi,
    private val beaconInfoCache: BeaconInfoCache,
) : BeaconResolver {

    override fun resolve(scanResults: Flow<List<ScanResult>>): Flow<Map<ScanResult, BeaconInfo>> {
        return scanResults.map {
            resolve(scanResults = it)
        }
    }

    override suspend fun resolve(scanResults: List<ScanResult>): Map<ScanResult, BeaconInfo> {
        val result = mutableMapOf<ScanResult, BeaconInfo>()
        val unknownScanResults = mutableListOf<ScanResult>()
        // Get beacon infos from cache if the beacon was already resolved.
        scanResults.forEach { scanResult ->
            val beacon = scanResult.beacon
            val cacheEntry = beaconInfoCache.get(beacon)
            when {
                cacheEntry != null -> cacheEntry.beaconInfo?.let { result[scanResult] = it }
                else -> unknownScanResults.add(scanResult)
            }
        }
        if (unknownScanResults.isEmpty()) {
            return result
        }
        // Execute the resolve request.
        val response = useCaseApi.resolve().execute(unknownScanResults)
        if (!response.isSuccess) {
            return result
        }
        unknownScanResults.forEach { scanResult ->
            val beaconInfo = response.getBeaconInfo(scanResult)
            beaconInfoCache.add(scanResult.beacon, beaconInfo)
            if (beaconInfo != null) {
                result[scanResult] = beaconInfo
            }
        }
        return result
    }
}

// Extensions

private fun ResolveResponse.getBeaconInfo(scanResult: ScanResult): BeaconInfo? {
    val results = body?.result
    if (results.isNullOrEmpty()) return null
    return when (val beacon = scanResult.beacon) {
        is EddystoneEid -> {
            val result = results.firstOrNull {
                it.eidValue == beacon.eid
            }
            result?.toBeaconInfo()
        }

        is IBeacon -> {
            val result = results.firstOrNull {
                it.eidValue == null && it.major == beacon.major && it.minor == beacon.minor
            }
            result?.toBeaconInfo()
        }

        else -> null
    }
}

private fun ResolveResponse.Body.Result.toBeaconInfo(): BeaconInfo? {
    return when {
        !valid -> null
        else -> {
            val vehicle = Vehicle(
                name = vehicleName,
                uic = uicVehicleNumber,
                decks = deckCount!!,
                transportationType = transportationType!!,
                organizationCode = organisationCode!!,
            )
            val installationLocation = InstallationLocation(
                name = mountingPointName,
                vehicle = vehicle,
                deck = deckPosition!!,
                deckName = deckName!!,
            )
            BeaconInfo(
                provider = provider,
                providerBeaconId = providerBeaconId,
                major = major!!,
                minor = minor!!,
                installationLocation = installationLocation,
            )
        }
    }
}
