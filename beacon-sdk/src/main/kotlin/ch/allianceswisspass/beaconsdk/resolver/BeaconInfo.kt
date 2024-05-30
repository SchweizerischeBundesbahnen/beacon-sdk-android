package ch.allianceswisspass.beaconsdk.resolver

/**
 * Information about a beacon.
 *
 * @property provider The provider of the beacon.
 * @property providerBeaconId The provider specific beacon id.
 * @property major The iBeacon major.
 * @property minor The iBeacon minor.
 * @property installationLocation The installation location of the beacon.
 */
data class BeaconInfo(
    val provider: String?,
    val providerBeaconId: String?,
    val major: Int,
    val minor: Int,
    val installationLocation: InstallationLocation,
)
