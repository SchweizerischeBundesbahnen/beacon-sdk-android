package ch.allianceswisspass.beaconsdk.usecaseapi.resolve

import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

internal data class ResolveResponse(
    val status: Int,
    val body: Body? = null,
    val errorBody: ErrorBody? = null,
) {

    /**
     * true if the request was successful, false otherwise.
     */
    val isSuccess by lazy { status == 200 }

    @Serializable
    data class Body(
        val result: List<Result>?,
    ) {
        @Serializable
        data class Result(
            val eidValue: String?,
            val valid: Boolean,
            val organisationCode: Int?,
            val major: Int?,
            val minor: Int?,
            val provider: String?,
            val providerBeaconId: String?,
            val uicVehicleNumber: Long?,
            val vehicleName: String?,
            val mountingPointName: String?,
            val deckPosition: Int?,
            val deckName: String?,
            val deckCount: Int?,
            val transportationType: String?,
        )
    }

    @Serializable
    data class ErrorBody(
        val error: String,
        val message: String,
        val traceId: String,
    )

    companion object {

        suspend fun fromHttpResponse(httpResponse: HttpResponse): ResolveResponse {
            return when (httpResponse.status) {
                HttpStatusCode.OK -> ResolveResponse(
                    status = httpResponse.status.value,
                    body = httpResponse.body(),
                )

                else -> ResolveResponse(
                    status = httpResponse.status.value,
                    errorBody = httpResponse.body(),
                )
            }
        }
    }
}
