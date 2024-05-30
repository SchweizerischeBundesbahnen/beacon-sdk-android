package ch.allianceswisspass.beaconsdk.scanner

/**
 * Kontakt.io Secure Profile without secure shuffling.
 *
 * @property uniqueId The unique id of the beacon.
 * @property deviceModel The device model of the beacon.
 * @property firmwareVersion The version of the firmware installed on the beacon. The list contains exactly two values:
 * major at index 0 and minor at index 1.
 * @property batteryLevel The battery level of the beacon in percent (integer value between 0 and 100) or 0xFF if not
 * applicable.
 * @property txPower The nominal tx power of the beacon.
 *
 * @see <a href="https://support.kontakt.io/hc/en-gb/articles/4413263824018-Secure-Profile-packet">kontakt.io</a>
 */
data class KontaktSecureProfile(
    val uniqueId: String,
    val deviceModel: Byte,
    val firmwareVersion: List<Int>,
    val batteryLevel: Int,
    val txPower: Int,
) : Beacon(
    type = BeaconType.KontaktSecureProfile,
)
