package ch.allianceswisspass.beaconsdk.usecaseapi.feedbacks

import io.ktor.client.statement.*
import kotlinx.serialization.Serializable

@Serializable
internal data class FeedbacksResponse(
    val status: Int,
) {

    /**
     * true if the request was successful, false otherwise.
     */
    val isSuccess by lazy { status == 200 }

    companion object {
        operator fun invoke(httpResponse: HttpResponse): FeedbacksResponse {
            return FeedbacksResponse(
                status = httpResponse.status.value,
            )
        }
    }
}
