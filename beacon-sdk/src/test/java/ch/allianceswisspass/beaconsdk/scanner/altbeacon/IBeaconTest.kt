package ch.allianceswisspass.beaconsdk.scanner.altbeacon

import android.bluetooth.BluetoothDevice
import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.IBeacon
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.ibeacon.IBeaconMapper
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.ibeacon.IBeaconParser
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalStdlibApi::class)
@RunWith(MockitoJUnitRunner::class)
class IBeaconTest {

    @Test
    fun `parse 60009-6`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")

        val hex = "1aff4c000215aea3" +
                "e3014bbc4ecfad17" +
                "2573922a5f4fea69" +
                "0006be0000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "000000000000"

        val scanData = hex.hexToByteArray()
        val parser = IBeaconParser()
        val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
        assertThat(beacon).isNotNull()
        val mapper = IBeaconMapper()
        val scanResult = mapper.map(beacon)
        assertThat(scanResult).isNotNull()
        assertThat(scanResult?.beacon?.type).isEqualTo(BeaconType.IBeacon)
        val iBeacon = scanResult!!.beacon as IBeacon
        assertThat(iBeacon.proximityUuid).isEqualTo("aea3e301-4bbc-4ecf-ad17-2573922a5f4f")
        assertThat(iBeacon.major).isEqualTo(60009)
        assertThat(iBeacon.minor).isEqualTo(6)
        assertThat(iBeacon.txPower).isEqualTo(-66)
    }

    @Test
    fun `parse 28006-1`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")

        val hex = "1aff4c000215aea3" +
                "e3014bbc4ecfad17" +
                "2573922a5f4f6d66" +
                "0001be0000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "000000000000"

        val scanData = hex.hexToByteArray()
        val parser = IBeaconParser()
        val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
        assertThat(beacon).isNotNull()
        val mapper = IBeaconMapper()
        val scanResult = mapper.map(beacon)
        assertThat(scanResult).isNotNull()
        assertThat(scanResult?.beacon?.type).isEqualTo(BeaconType.IBeacon)
        val iBeacon = scanResult!!.beacon as IBeacon
        assertThat(iBeacon.proximityUuid).isEqualTo("aea3e301-4bbc-4ecf-ad17-2573922a5f4f")
        assertThat(iBeacon.major).isEqualTo(28006)
        assertThat(iBeacon.minor).isEqualTo(1)
        assertThat(iBeacon.txPower).isEqualTo(-66)
    }

    @Test
    fun `parse - invalid proximity uuid`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")

        val hex = "1aff4c0002155076" +
                "5cb7d9ea4e2199a4" +
                "fa879613a4922f7e" +
                "a53fce0000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "000000000000"

        val scanData = hex.hexToByteArray()
        val parser = IBeaconParser()
        val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
        assertThat(beacon).isNotNull()
        val mapper = IBeaconMapper()
        val scanResult = mapper.map(beacon)
        assertThat(scanResult).isNull()
    }
}
