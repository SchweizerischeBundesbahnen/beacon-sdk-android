package ch.allianceswisspass.beaconsdk.usecaseapi.auth

internal object AccessTokenHolder {

    var accessToken: AccessToken? = null
        get() = when {
            field?.isExpired != false -> null
            else -> field
        }
        set(value) = when {
            value?.isExpired != false -> field = value
            else -> field = value
        }
}
