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
                "0dfe" +
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
    }
}
