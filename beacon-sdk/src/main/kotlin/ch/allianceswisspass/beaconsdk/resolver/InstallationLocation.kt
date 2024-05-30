package ch.allianceswisspass.beaconsdk.resolver

/**
 * Installation location of a beacon in a vehicle.
 *
 * @property name The name of the installation location.
 * @property vehicle The vehicle in which the Beacon is installed.
 * @property deck The deck on which the beacon is installed.
 * @property deckName The name of the deck on which the beacon is installed.
 */
data class InstallationLocation(
    val name: String?,
    val vehicle: Vehicle,
    val deck: Int,
    val deckName: String,
)
