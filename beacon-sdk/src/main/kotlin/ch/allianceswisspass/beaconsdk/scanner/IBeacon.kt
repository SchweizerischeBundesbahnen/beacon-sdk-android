package ch.allianceswisspass.beaconsdk.scanner

/**
 * iBeacon
 *
 * @property proximityUuid A unique ID that identifies an iBeacon or a set of iBeacons as being of a certain type or
 * from a certain organization.
 * @property major A major identifier, to differentiate this iBeacon from other beacons with the same proximity UUID.
 * @property minor A minor identifier, to differentiate this beacon from other beacons with the same proximity UUID and
 * major identifier.
 * @property txPower The calibrated tx power at 1 meter measured in dBm.
 *
 * @see <a href="https://en.wikipedia.org/wiki/IBeacon">Wikipedia</a>
 * @see <a href="https://developer.apple.com/ibeacon/Getting-Started-with-iBeacon.pdf">Getting Started with iBeacon</a>
 */
data class IBeacon(
    val proximityUuid: String,
    val major: Int,
    val minor: Int,
    val txPower: Int,
) : Beacon(
    type = BeaconType.IBeacon,
)
