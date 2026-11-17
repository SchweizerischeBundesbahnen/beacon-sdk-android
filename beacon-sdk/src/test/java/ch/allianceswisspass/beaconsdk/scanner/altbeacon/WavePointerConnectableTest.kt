package ch.allianceswisspass.beaconsdk.scanner.altbeacon

import android.bluetooth.BluetoothDevice
import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.WavePointerConnectable
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.wavepointer.WavePointerConnectableMapper
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.wavepointer.WavePointerConnectableParser
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalStdlibApi::class)
@RunWith(MockitoJUnitRunner::class)
class WavePointerConnectableTest {

    @Test
    fun `parse WP003031`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")
        Mockito.`when`(mockDevice.name).thenReturn("WP003031")

        val hex = "1dff" +
                "2004beac" +
                "aea3e3014bbc4ecfad172573922a5f4f" +
                "ea6e" +
                "0004" +
                "0df2" +
                "0000" +
                "0dff2004" +
                "0e23140000003031" +
                "0e" +
                "13" +
                "090957503030333033310000000000000000"

        val scanData = hex.hexToByteArray()
        val parser = WavePointerConnectableParser()
        val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
        assertThat(beacon).isNotNull()
        val mapper = WavePointerConnectableMapper()
        val scanResult = mapper.map(beacon)
        assertThat(scanResult).isNotNull()
        assertThat(scanResult?.beacon?.type).isEqualTo(BeaconType.WavePointerConnectable)
        val wavePointerConnectable = scanResult!!.beacon as WavePointerConnectable
        assertThat(wavePointerConnectable.serialNumber).isEqualTo("E23140000003031")
        assertThat(wavePointerConnectable.batteryVoltage).isEqualTo(3570)
    }

    @Test
    fun `parse WP003033`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")
        Mockito.`when`(mockDevice.name).thenReturn("WP003033")

        val hex = "1dff" +
                "2004beac" +
                "aea3e3014bbc4ecfad172573922a5f4f" +
                "ea69" +
                "0004" +
                "0dfe" +
                "0000" +
                "0dff2004" +
                "0e23140000003033" +
                "0e" +
                "14" +
                "090957503030333033330000000000000000"

        val scanData = hex.hexToByteArray()
        val parser = WavePointerConnectableParser()
        val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
        assertThat(beacon).isNotNull()
        val mapper = WavePointerConnectableMapper()
        val scanResult = mapper.map(beacon)
        assertThat(scanResult).isNotNull()
        assertThat(scanResult?.beacon?.type).isEqualTo(BeaconType.WavePointerConnectable)
        val wavePointerConnectable = scanResult!!.beacon as WavePointerConnectable
        assertThat(wavePointerConnectable.serialNumber).isEqualTo("E23140000003033")
        assertThat(wavePointerConnectable.batteryVoltage).isEqualTo(3582)
    }

    @Test
    fun `parse WP003034`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")
        Mockito.`when`(mockDevice.name).thenReturn("WP003034")

        val hex = "1dff" +
                "2004beac" +
                "aea3e3014bbc4ecfad172573922a5f4f" +
                "ea69" +
                "0002" +
                "0d9e" +
                "0000" +
                "0dff2004" +
                "0e23140000003034" +
                "0e" +
                "16" +
                "090957503030333033340000000000000000"

        val scanData = hex.hexToByteArray()
        val parser = WavePointerConnectableParser()
        val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
        assertThat(beacon).isNotNull()
        val mapper = WavePointerConnectableMapper()
        val scanResult = mapper.map(beacon)
        assertThat(scanResult).isNotNull()
        assertThat(scanResult?.beacon?.type).isEqualTo(BeaconType.WavePointerConnectable)
        val wavePointerConnectable = scanResult!!.beacon as WavePointerConnectable
        assertThat(wavePointerConnectable.serialNumber).isEqualTo("E23140000003034")
        assertThat(wavePointerConnectable.batteryVoltage).isEqualTo(3486)
    }

    @Test
    fun `parse multiple battery voltage values`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")
        Mockito.`when`(mockDevice.name).thenReturn("WP003034")

        val batteryVoltages = mapOf(
            "07d0" to 2000,
            "0834" to 2100,
            "0898" to 2200,
            "08fc" to 2300,
            "0960" to 2400,
            "09c4" to 2500,
            "0a28" to 2600,
            "0a8c" to 2700,
            "0af0" to 2800,
            "0b54" to 2900,
            "0c18" to 3096,
            "0c2a" to 3114,
            "0d9e" to 3486,
            "0daa" to 3498,
            "0df8" to 3576,
            "0e0a" to 3594,
            "0e10" to 3600,
        )

        batteryVoltages.forEach { (hexBatteryVoltage, batteryVoltage) ->
            val hex = "1dff" +
                    "2004beac" +
                    "aea3e3014bbc4ecfad172573922a5f4f" +
                    "ea69" +
                    "0002" +
                    hexBatteryVoltage +
                    "0000" +
                    "0dff2004" +
                    "0e23140000003034" +
                    "0e" +
                    "16" +
                    "090957503030333033340000000000000000"
            val scanData = hex.hexToByteArray()
            val parser = WavePointerConnectableParser()
            val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
            val mapper = WavePointerConnectableMapper()
            val scanResult = mapper.map(beacon)
            val wavePointerConnectable = scanResult!!.beacon as WavePointerConnectable
            assertThat(wavePointerConnectable.batteryVoltage).isEqualTo(batteryVoltage)
        }
    }
}
