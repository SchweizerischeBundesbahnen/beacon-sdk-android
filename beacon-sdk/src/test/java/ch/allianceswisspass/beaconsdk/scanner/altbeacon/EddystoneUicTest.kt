package ch.allianceswisspass.beaconsdk.scanner.altbeacon

import android.bluetooth.BluetoothDevice
import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.EddystoneUic
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.eddystone.uic.EddystoneUicMapper
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.eddystone.uic.EddystoneUicParser
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalStdlibApi::class)
@RunWith(MockitoJUnitRunner::class)
class EddystoneUicTest {

    @Test
    fun `parse 508516940063D35`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")

        val hex = "0201060303aafe15" +
                "16aafe0000003530" +
                "3835313639343030" +
                "3633443335000000" +
                "0000000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "000000000000"

        val scanData = hex.hexToByteArray()
        val parser = EddystoneUicParser()
        val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
        assertThat(beacon).isNotNull()
        val mapper = EddystoneUicMapper()
        val scanResult = mapper.map(beacon)
        assertThat(scanResult).isNotNull()
        assertThat(scanResult?.beacon?.type).isEqualTo(BeaconType.EddystoneUic)
        val eddystoneUic = scanResult!!.beacon as EddystoneUic
        assertThat(eddystoneUic.txPower).isEqualTo(0)
        assertThat(eddystoneUic.uic).isEqualTo("508516940063")
        assertThat(eddystoneUic.installationLocationName).isEqualTo("D35")
    }

    @Test
    fun `parse 948515000081D56`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")

        val hex = "0201060303aafe15" +
                "16aafe0000003934" +
                "3835313530303030" +
                "3831443536000000" +
                "0000000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "000000000000"

        val scanData = hex.hexToByteArray()
        val parser = EddystoneUicParser()
        val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
        assertThat(beacon).isNotNull()
        val mapper = EddystoneUicMapper()
        val scanResult = mapper.map(beacon)
        assertThat(scanResult).isNotNull()
        assertThat(scanResult?.beacon?.type).isEqualTo(BeaconType.EddystoneUic)
        val eddystoneUic = scanResult!!.beacon as EddystoneUic
        assertThat(eddystoneUic.txPower).isEqualTo(0)
        assertThat(eddystoneUic.uic).isEqualTo("948515000081")
        assertThat(eddystoneUic.installationLocationName).isEqualTo("D56")
    }

    @Test
    fun `parse - uid`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")

        val hex = "0303aafe1516aafe" +
                "00bf6676543ea910" +
                "eee5a3342d1847ff" +
                "a390000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "000000000000"

        val scanData = hex.hexToByteArray()
        val parser = EddystoneUicParser()
        val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
        assertThat(beacon).isNotNull()
        val mapper = EddystoneUicMapper()
        val scanResult = mapper.map(beacon)
        assertThat(scanResult).isNull()
    }
}
