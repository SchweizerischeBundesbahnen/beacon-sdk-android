package ch.allianceswisspass.beaconsdk.scanner.altbeacon

import android.bluetooth.BluetoothDevice
import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.KontaktSecureProfile
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.kontakt.KontaktSecureProfileMapper
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.kontakt.KontaktSecureProfileParser
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalStdlibApi::class)
@RunWith(MockitoJUnitRunner::class)
class KontaktSecureProfileTest {

    @Test
    fun `parse nutkaj`() {

        val mockDevice = Mockito.mock(BluetoothDevice::class.java)
        Mockito.`when`(mockDevice.address).thenReturn("FF:FF:FF:FF:FF:FF")
        Mockito.`when`(mockDevice.name).thenReturn("kontakt_beacon")

        val hex = "0201060f166afe02" +
                "06020064f46e7574" +
                "6b616a0409556c69" +
                "0000000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "0000000000000000" +
                "000000000000"

        val scanData = hex.hexToByteArray()
        val parser = KontaktSecureProfileParser()
        val beacon = parser.fromScanData(scanData, 0, mockDevice, 0)
        assertThat(beacon).isNotNull()
        val mapper = KontaktSecureProfileMapper()
        val scanResult = mapper.map(beacon)
        assertThat(scanResult).isNotNull()
        assertThat(scanResult?.beacon?.type).isEqualTo(BeaconType.KontaktSecureProfile)
        val kontaktSecureProfile = scanResult!!.beacon as KontaktSecureProfile
        assertThat(kontaktSecureProfile.uniqueId).isEqualTo("nutkaj")
        assertThat(kontaktSecureProfile.deviceModel).isEqualTo(6)
        assertThat(kontaktSecureProfile.firmwareVersion).isEqualTo(listOf(2, 0))
        assertThat(kontaktSecureProfile.batteryLevel).isEqualTo(100)
        assertThat(kontaktSecureProfile.txPower).isEqualTo(-12)
    }
}
