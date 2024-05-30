package ch.allianceswisspass.beaconsdk.scanner

/**
 * Eddystone EID.
 *
 * @property txPower The calibrated tx power at 0 meters measured in dBm.
 * @property eid The ephemeral identifier.
 *
 * @see <a href="https://github.com/google/eddystone/tree/master/eddystone-eid">Eddystone-EID</a>
 */
data class EddystoneEid(
    val txPower: Int,
    val eid: String,
) : Beacon(
    type = BeaconType.EddystoneEid,
)
