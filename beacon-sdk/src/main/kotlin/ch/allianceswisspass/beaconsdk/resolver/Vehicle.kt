package ch.allianceswisspass.beaconsdk.resolver

/**
 * Vehicle.
 *
 * @property name The name of the vehicle.
 * @property uic The UIC wagon number.
 * @property decks The number of decks.
 * @property transportationType The transportation type of the vehicle.
 * @property organizationCode The organization code of the vehicle owner.
 *
 * @see <a href="https://en.wikipedia.org/wiki/UIC_wagon_numbers">UIC wagon numbers</a>
 */
data class Vehicle(
    val name: String?,
    val uic: Long?,
    val decks: Int,
    val transportationType: String,
    val organizationCode: Int,
)
