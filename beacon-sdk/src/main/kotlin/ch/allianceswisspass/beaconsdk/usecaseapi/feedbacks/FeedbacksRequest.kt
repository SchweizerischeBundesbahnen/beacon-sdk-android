package ch.allianceswisspass.beaconsdk.usecaseapi.feedbacks

import ch.allianceswisspass.beaconsdk.scanner.*
import ch.allianceswisspass.beaconsdk.usecaseapi.auth.Authenticator
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory
import java.util.*

internal class FeedbacksRequest(
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

    suspend fun execute(scanResults: List<ScanResult>): FeedbacksResponse {
        // Check the scan results argument.
        when {
            // If the scan results are empty, it is not necessary to execute a request.
            scanResults.isEmpty() -> {
                return FeedbacksResponse(status = 200)
            }
            // The maximum allowed size of scan results is 100.
            scanResults.size > 100 -> {
                throw IllegalArgumentException("Too many scan results (${scanResults.size}). Max 100 allowed.")
            }
        }
        val body = Body(
            feedbacks = listOf(
                FeedbackDto(
                    platformId = platformId,
                    platformVersion = platformVersion,
                    referenceTimestampInSec = Date().time / 1000,
                    appId = appId,
                    appVersion = appVersion,
                    installationId = installationId,
                    sessionId = sessionId,
                    scanResults = scanResults.mapNotNull { it.toDto() },
                ),
            ),
        )
        val accessToken = authenticator.accessToken()
        val response: HttpResponse = httpClient.post {
            url("$baseUrl/api/v3/feedbacks")
            contentType(ContentType.Application.Json)
            bearerAuth(accessToken.value)
            setBody(body)
        }
        return FeedbacksResponse(response)
    }
}

// DTOs

@Serializable
private data class Body(
    val feedbacks: List<FeedbackDto>,
)

@Serializable
private data class FeedbackDto(
    val platformId: String,
    val platformVersion: String,
    val appId: String,
    val appVersion: String?,
    val installationId: String,
    val sessionId: String?,
    val referenceTimestampInSec: Long,
    val scanResults: List<ScanResultDto>,
)

// Scan result

@Serializable
private data class ScanResultDto(
    val scanTimestampInSec: Long,
    val deviceName: String? = null,
    val eddystoneEid: EddystoneEidDto? = null,
    val eddystoneUic: EddystoneUicDto? = null,
    val ibeacon: IBeaconDto? = null,
    val kontaktSecureProfile: KontaktSecureProfileDto? = null,
    val wavePointerConnectable: WavePointerConnectableDto? = null,
)

private fun ScanResult.toDto(): ScanResultDto? {
    return when (beacon) {
        is EddystoneEid -> ScanResultDto(
            scanTimestampInSec = timestamp.time / 1000,
            eddystoneEid = beacon.toDto(),
        )

        is EddystoneUic -> ScanResultDto(
            scanTimestampInSec = timestamp.time / 1000,
            eddystoneUic = beacon.toDto(),
        )

        is IBeacon -> ScanResultDto(
            scanTimestampInSec = timestamp.time / 1000,
            ibeacon = beacon.toDto(),
        )

        is KontaktSecureProfile -> ScanResultDto(
            scanTimestampInSec = timestamp.time / 1000,
            deviceName = deviceName,
            kontaktSecureProfile = beacon.toDto(),
        )

        is WavePointerConnectable -> ScanResultDto(
            scanTimestampInSec = timestamp.time / 1000,
            deviceName = deviceName,
            wavePointerConnectable = beacon.toDto(),
        )

        else -> {
            val logger = LoggerFactory.getLogger(FeedbacksRequest::class.java)
            logger.debug("Unknown beacon type {}", beacon::class.java)
            null
        }
    }
}

// Eddystone EID

@Serializable
private data class EddystoneEidDto(
    val txPower: Int?,
    val eid: String,
)

private fun EddystoneEid.toDto(): EddystoneEidDto {
    return EddystoneEidDto(
        txPower = txPower,
        eid = eid,
    )
}

// Eddystone UIC

@Serializable
private data class EddystoneUicDto(
    val txPower: Int?,
    val uic: String,
    val installationLocation: String,
)

private fun EddystoneUic.toDto(): EddystoneUicDto {
    return EddystoneUicDto(
        txPower = txPower,
        uic = uic,
        installationLocation = installationLocationName,
    )
}

// iBeacon

@Serializable
private data class IBeaconDto(
    val txPower: Int?,
    val major: Int,
    val minor: Int,
)

private fun IBeacon.toDto(): IBeaconDto {
    return IBeaconDto(
        txPower = txPower,
        major = major,
        minor = minor,
    )
}

// KontaktSecureProfile

@Serializable
private data class KontaktSecureProfileDto(
    val uniqueId: String,
    val deviceModel: Byte,
    val firmwareVersion: String,
    val batteryLevel: Int,
    val txPower: Int,
)

private fun KontaktSecureProfile.toDto(): KontaktSecureProfileDto {
    return KontaktSecureProfileDto(
        uniqueId = uniqueId,
        deviceModel = deviceModel,
        firmwareVersion = firmwareVersion.joinToString("."),
        batteryLevel = batteryLevel,
        txPower = txPower,
    )
}

// WavePointerConnectable

@Serializable
private data class WavePointerConnectableDto(
    val uuid: String,
    val major: Int,
    val minor: Int,
    val serialNumber: String,
    val batteryVoltage: Int,
    val firmwareVersion: UByte,
    val configurationVersion: UByte,
)

private fun WavePointerConnectable.toDto(): WavePointerConnectableDto {
    return WavePointerConnectableDto(
        uuid = uuid,
        major = major,
        minor = minor,
        serialNumber = serialNumber,
        batteryVoltage = batteryVoltage,
        firmwareVersion = firmwareVersion,
        configurationVersion = configurationVersion,
    )
}
