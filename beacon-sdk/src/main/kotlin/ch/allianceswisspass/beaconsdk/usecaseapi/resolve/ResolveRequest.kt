package ch.allianceswisspass.beaconsdk.usecaseapi.resolve

import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.EddystoneEid
import ch.allianceswisspass.beaconsdk.scanner.IBeacon
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import ch.allianceswisspass.beaconsdk.usecaseapi.auth.Authenticator
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import java.util.*

internal class ResolveRequest(
    private val httpClient: HttpClient,
    private val authenticator: Authenticator,
    private val baseUrl: String,
    private val platformId: String,
    private val platformVersion: String,
    private val appId: String,
    private val appVersion: String? = null,
    private val installationId: String,
    private val sessionId: String? = null,
) {

    suspend fun execute(scanResults: List<ScanResult>): ResolveResponse {
        // If the scan results do not contain EddystoneEid or iBeacons , it is not necessary to execute a request
        // because the response will always be empty.
        val returnEmptyResponse = scanResults.none {
            it.beacon.type == BeaconType.EddystoneEid || it.beacon.type == BeaconType.IBeacon
        }
        if (returnEmptyResponse) {
            return ResolveResponse(
                status = 200,
                body = ResolveResponse.Body(result = emptyList()),
            )
        }
        val body = Body(
            platformId = platformId,
            platformVersion = platformVersion,
            appId = appId,
            appVersion = appVersion,
            installationId = installationId,
            sessionId = sessionId,
            referenceTimestampInSec = Date().time / 1000,
            values = scanResults.mapNotNull { it.toValue() },
        )
        val accessToken = authenticator.accessToken()
        val response: HttpResponse = httpClient.post {
            url("$baseUrl/api/v3/resolve")
            contentType(ContentType.Application.Json)
            bearerAuth(accessToken.value)
            setBody(body)
        }
        return ResolveResponse.fromHttpResponse(response)
    }
}

// DTOs

@Serializable
private data class Body(
    val platformId: String?,
    val platformVersion: String?,
    val appId: String?,
    val appVersion: String?,
    val installationId: String?,
    val sessionId: String?,
    val referenceTimestampInSec: Long?,
    val values: List<Value>,
)

@Serializable
private data class Value(
    val eid: String? = null,
    val major: Int? = null,
    val minor: Int? = null,
    val scanTimestampInSec: Long,
)

private fun ScanResult.toValue(): Value? {
    return when (beacon) {
        is EddystoneEid -> Value(
            scanTimestampInSec = timestamp.time / 1000,
            eid = beacon.eid,
        )

        is IBeacon -> Value(
            scanTimestampInSec = timestamp.time / 1000,
            major = beacon.major,
            minor = beacon.minor,
        )

        else -> null
    }
}
