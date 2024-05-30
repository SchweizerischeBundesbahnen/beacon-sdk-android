package ch.allianceswisspass.beaconsdk.scanner

import ch.allianceswisspass.beaconsdk.usecaseapi.UseCaseApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.util.*

internal class FeedbacksSender(
    private val maxBufferSize: Int = 100,
    private val useCaseApi: UseCaseApi,
) {

    private val logger = LoggerFactory.getLogger(FeedbacksSender::class.java)
    private val buffer = mutableMapOf<Key, ScanResult>()

    fun add(scanResults: List<ScanResult>) {
        scanResults.forEach { scanResult ->
            val key = scanResult.key
            val value = buffer[key]
            buffer[key] = when {
                value == null -> scanResult
                value.timestamp.before(scanResult.timestamp) -> scanResult
                else -> value
            }
        }
        if (buffer.size >= maxBufferSize) {
            flush()
        }
    }

    fun flush() {
        val scanResults = buffer.values.chunked(maxBufferSize)
        buffer.clear()
        scanResults.forEach {
            executeRequest(scanResults = it)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun executeRequest(scanResults: List<ScanResult>) {
        GlobalScope.launch {
            try {
                val request = useCaseApi.feedbacks()
                val response = request.execute(scanResults = scanResults)
                if (!response.isSuccess) {
                    logger.debug("{} - Sending feedbacks failed.", response.status)
                }
            } catch (e: Throwable) {
                logger.debug("Sending feedbacks failed.", e)
            }
        }
    }
}

private data class Key(
    val day: Int,
    val hour: Int,
    val minute: Int,
    val beacon: Beacon,
)

private val ScanResult.key: Key
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = timestamp
        var minute = calendar.get(Calendar.MINUTE)
        minute -= (minute % 5)
        return Key(
            day = calendar.get(Calendar.DAY_OF_YEAR),
            hour = calendar.get(Calendar.HOUR_OF_DAY),
            minute = minute,
            beacon = beacon,
        )
    }
