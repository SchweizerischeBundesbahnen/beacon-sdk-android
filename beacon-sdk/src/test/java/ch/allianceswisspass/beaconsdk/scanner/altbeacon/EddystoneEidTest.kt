package ch.allianceswisspass.beaconsdk.scanner.altbeacon

import android.bluetooth.BluetoothDevice
import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.EddystoneEid
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.eddystone.eid.EddystoneEidMapper
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.eddystone.eid.EddystoneEidParser
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalStdlibApi::class)
@RunWith(MockitoJUnitRunner::class)
class EddystoneEidTest {

    @Test
    fun `parse c22fe1f198109681`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")

        val hex = "0201060303aafe0d" +
                "16aafe30bec22fe1" +
                "f198109681000000" +
                "0000000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "000000000000"

        val scanData = hex.hexToByteArray()
        val parser = EddystoneEidParser()
        val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
        assertThat(beacon).isNotNull()
        val mapper = EddystoneEidMapper()
        val scanResult = mapper.map(beacon)
        assertThat(scanResult).isNotNull()
        assertThat(scanResult?.beacon?.type).isEqualTo(BeaconType.EddystoneEid)
        val eddystoneEid = scanResult!!.beacon as EddystoneEid
        assertThat(eddystoneEid.txPower).isEqualTo(-66)
        assertThat(eddystoneEid.eid).isEqualTo("c22fe1f198109681")
    }

    @Test
    fun `parse 7dde653de088f234`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")

        val hex = "0201060303aafe0d" +
                "16aafe30be7dde65" +
                "3de088f234000000" +
                "0000000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "000000000000"

        val scanData = hex.hexToByteArray()
        val parser = EddystoneEidParser()
        val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
        assertThat(beacon).isNotNull()
        val mapper = EddystoneEidMapper()
        val scanResult = mapper.map(beacon)
        assertThat(scanResult).isNotNull()
        assertThat(scanResult?.beacon?.type).isEqualTo(BeaconType.EddystoneEid)
        val eddystoneEid = scanResult!!.beacon as EddystoneEid
        assertThat(eddystoneEid.txPower).isEqualTo(-66)
        assertThat(eddystoneEid.eid).isEqualTo("7dde653de088f234")
    }
}
