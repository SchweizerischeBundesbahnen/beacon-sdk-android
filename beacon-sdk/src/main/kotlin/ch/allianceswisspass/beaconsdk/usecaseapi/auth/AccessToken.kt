package ch.allianceswisspass.beaconsdk.usecaseapi.auth

import java.util.*

internal data class AccessToken(
    val type: String,
    val value: String,
    val expireTime: Date
) {
    /**
     * Returns whether this access token is expired.
     */
    val isExpired: Boolean
        get() {
            val now = Date()
            return now.after(expireTime)
        }
}
