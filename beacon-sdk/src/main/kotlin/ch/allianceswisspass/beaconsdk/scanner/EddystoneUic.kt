package ch.allianceswisspass.beaconsdk.scanner

/**
 * Eddystone UIC.
 *
 * @property txPower The calibrated tx power at 0 meters measured in dBm.
 * @property uic The unique wagon number of the wagon.
 * @property installationLocationName The name of the location of the beacon inside the wagon.
 *
 * @see <a href="https://github.com/google/eddystone/tree/master/eddystone-uid">Eddystone-UID</a>
 */
data class EddystoneUic(
    val txPower: Int,
    val uic: String,
    val installationLocationName: String,
) : Beacon(
    type = BeaconType.EddystoneUic,
)
