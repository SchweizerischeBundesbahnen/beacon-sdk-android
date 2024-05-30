package ch.allianceswisspass.beaconsdk.usecaseapi

import ch.allianceswisspass.beaconsdk.scanner.*
import ch.allianceswisspass.beaconsdk.usecaseapi.auth.Authenticator
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class UseCaseApiTest {

    private val authenticator: Authenticator by lazy {
        Authenticator(
            tokenUrl = "https://login.microsoftonline.com/2cda5d11-f0ac-46b3-967d-af1b2e1bd01a/oauth2/v2.0/token",
            clientId = "b729770b-bb21-41dd-8a90-2591d069fe9c",
            clientSecret = "wqo8Q~nLgdLUSwuieBTLOg-ntfjWuQWUAydtudBk",
            scope = "api://75c2c871-016c-4677-ad25-e5b08b104257/.default",
        )
    }

    private val api: UseCaseApi by lazy {
        UseCaseApi(
            authenticator = authenticator,
            baseUrl = "https://beacon-int.api.sbb.ch:443",
            platformId = "Android",
            platformVersion = "0",
            appId = "ch.allianceswisspass.beaconsdk.test",
            appVersion = "0.0.1+1",
            installationId = "ae9e4fc3-f19f-4915-ba90-00f030d26cb3",
            sessionId = UUID.randomUUID().toString(),
        )
    }

    private val eddystoneEid: EddystoneEid
        get() = EddystoneEid(
            txPower = -12,
            eid = "0edac6bb0c1b7bab",
        )

    private val eddystoneUic: EddystoneUic
        get() = EddystoneUic(
            txPower = -12,
            uic = "508516940063",
            installationLocationName = "D35",
        )

    private val iBeacon: IBeacon
        get() = IBeacon(
            proximityUuid = "2d04ce68-3f17-4ef8-a339-5d5b646b4c9b",
            major = 60009,
            minor = 6,
            txPower = -12,
        )

    private val kontaktSecureProfile: KontaktSecureProfile
        get() = KontaktSecureProfile(
            uniqueId = "BusMJd",
            deviceModel = 6,
            firmwareVersion = listOf(1, 1),
            batteryLevel = 65,
            txPower = -12,
        )

    private val wavePointerConnectable: WavePointerConnectable
        get() = WavePointerConnectable(
            uuid = "2d04ce68-3f17-4ef8-a339-5d5b646b4c9b",
            major = 60009,
            minor = 6,
            serialNumber = "E320000000003034",
            batteryVoltage = -3756,
            firmwareVersion = 14u,
            configurationVersion = 5u,
        )

    @Test
    fun testFeedbacks() = runBlocking {
        val scanTimestamp = Date()
        // Eddystone EID
        val mockScanResult1 = Mockito.mock(ScanResult::class.java)
        Mockito.`when`(mockScanResult1.timestamp).thenReturn(scanTimestamp)
        Mockito.`when`(mockScanResult1.beacon).thenReturn(eddystoneEid)
        // Eddystone UIC
        val mockScanResult2 = Mockito.mock(ScanResult::class.java)
        Mockito.`when`(mockScanResult2.timestamp).thenReturn(scanTimestamp)
        Mockito.`when`(mockScanResult2.beacon).thenReturn(eddystoneUic)
        // iBeacon
        val mockScanResult3 = Mockito.mock(ScanResult::class.java)
        Mockito.`when`(mockScanResult3.timestamp).thenReturn(scanTimestamp)
        Mockito.`when`(mockScanResult3.beacon).thenReturn(iBeacon)
        // KontaktSecureProfile
        val mockScanResult4 = Mockito.mock(ScanResult::class.java)
        Mockito.`when`(mockScanResult4.timestamp).thenReturn(scanTimestamp)
        Mockito.`when`(mockScanResult4.deviceName).thenReturn("948535000400D51")
        Mockito.`when`(mockScanResult4.beacon).thenReturn(kontaktSecureProfile)
        // WavePointerConnectable
        val mockScanResult5 = Mockito.mock(ScanResult::class.java)
        Mockito.`when`(mockScanResult5.timestamp).thenReturn(scanTimestamp)
        Mockito.`when`(mockScanResult5.deviceName).thenReturn("WP003034")
        Mockito.`when`(mockScanResult5.beacon).thenReturn(wavePointerConnectable)

        val response = api.feedbacks().execute(
            scanResults = listOf(
                mockScanResult1,
                mockScanResult2,
                mockScanResult3,
                mockScanResult4,
                mockScanResult5,
            ),
        )
        assertThat(response).isNotNull()
        assertThat(response.status).isEqualTo(200)
    }

    @Test
    fun testResolve() = runBlocking {
        val scanTimestamp = Date()
        // Eddystone EID
        val mockScanResult1 = Mockito.mock(ScanResult::class.java)
        Mockito.`when`(mockScanResult1.timestamp).thenReturn(scanTimestamp)
        Mockito.`when`(mockScanResult1.beacon).thenReturn(eddystoneEid)
        // iBeacon
        val mockScanResult2 = Mockito.mock(ScanResult::class.java)
        Mockito.`when`(mockScanResult2.timestamp).thenReturn(scanTimestamp)
        Mockito.`when`(mockScanResult2.beacon).thenReturn(iBeacon)

        val response = api.resolve().execute(
            scanResults = listOf(
                mockScanResult1,
                mockScanResult2,
            ),
        )
        assertThat(response).isNotNull()
        assertThat(response.status).isEqualTo(200)
        assertThat(response.body).isNotNull()
        assertThat(response.body?.result).isNotNull()
        assertThat(response.body?.result).isNotEmpty()
    }
}
