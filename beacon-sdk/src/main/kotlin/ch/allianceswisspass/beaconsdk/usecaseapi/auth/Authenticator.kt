package ch.allianceswisspass.beaconsdk.usecaseapi.auth

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import java.util.*

internal class Authenticator(
    private val tokenUrl: String,
    private val clientId: String,
    private val clientSecret: String,
    private val scope: String,
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
                level = LogLevel.HEADERS
            }
        }
    }

    suspend fun accessToken(): AccessToken {
        var accessToken = AccessTokenHolder.accessToken
        if (accessToken != null) {
            return accessToken
        }
        val now = Date()
        val response = requestToken()
        accessToken = AccessToken(
            type = response.tokenType,
            value = response.accessToken,
            expireTime = Date(now.time + response.expiresIn * 900)
        )
        AccessTokenHolder.accessToken = accessToken
        return accessToken
    }

    private suspend fun requestToken(): TokenResponse {
        val response: HttpResponse = httpClient.submitForm(
            url = tokenUrl,
            formParameters = parameters {
                append("client_id", clientId)
                append("client_secret", clientSecret)
                append("scope", scope)
                append("grant_type", "client_credentials")
            },
        )
        return response.body()
    }
}
