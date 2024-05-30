package ch.allianceswisspass.beaconsdk.usecaseapi

import ch.allianceswisspass.beaconsdk.usecaseapi.auth.Authenticator
import ch.allianceswisspass.beaconsdk.usecaseapi.feedbacks.FeedbacksRequest
import ch.allianceswisspass.beaconsdk.usecaseapi.resolve.ResolveRequest
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

internal class UseCaseApi(
    private val authenticator: Authenticator,
    private val baseUrl: String,
    private val platformId: String,
    private val platformVersion: String,
    private val appId: String,
    private val appVersion: String? = null,
    private val installationId: String,
    private val sessionId: String? = null,
) {

    @OptIn(ExperimentalSerializationApi::class)
    private val httpClient: HttpClient by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    explicitNulls = false
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
                sanitizeHeader { header ->
                    header == HttpHeaders.Authorization
                }
            }
        }
    }

    fun feedbacks(): FeedbacksRequest {
        return FeedbacksRequest(
            httpClient = httpClient,
            authenticator = authenticator,
            baseUrl = baseUrl,
            platformId = platformId,
            platformVersion = platformVersion,
            appId = appId,
            appVersion = appVersion,
            installationId = installationId,
            sessionId = sessionId,
        )
    }

    fun resolve(): ResolveRequest {
        return ResolveRequest(
            httpClient = httpClient,
            authenticator = authenticator,
            baseUrl = baseUrl,
            platformId = platformId,
            platformVersion = platformVersion,
            appId = appId,
            appVersion = appVersion,
            installationId = installationId,
            sessionId = sessionId,
        )
    }
}
