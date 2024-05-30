package ch.allianceswisspass.beaconsdk.example

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Format {

    fun meters(value: Double): String {
        val format = DecimalFormat("#.##")
        return "${format.format(value)} m"
    }

    fun milliVolt(value: Int): String {
        return "$value mV"
    }

    fun percent(value: Int): String {
        return "$value%"
    }

    fun rssi(value: Int): String {
        return "$value dBm"
    }

    fun time(date: Date, showSeconds: Boolean = false): String {
        val format = when {
            showSeconds -> SimpleDateFormat("HH:mm:ss", Locale.US)
            else -> SimpleDateFormat("HH:mm", Locale.US)
        }
        return format.format(date)
    }

    fun txPower(value: Int): String {
        return "$value dBm"
    }
}
