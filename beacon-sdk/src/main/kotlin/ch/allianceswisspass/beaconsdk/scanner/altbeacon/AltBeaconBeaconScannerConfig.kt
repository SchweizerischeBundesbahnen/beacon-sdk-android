package ch.allianceswisspass.beaconsdk.scanner.altbeacon

import android.app.Notification
import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.eddystone.eid.EddystoneEidMapper
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.eddystone.eid.EddystoneEidParser
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.eddystone.uic.EddystoneUicMapper
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.eddystone.uic.EddystoneUicParser
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.ibeacon.IBeaconMapper
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.ibeacon.IBeaconParser
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.kontakt.KontaktSecureProfileMapper
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.kontakt.KontaktSecureProfileParser
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.wavepointer.WavePointerConnectableMapper
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.wavepointer.WavePointerConnectableParser
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Region

internal data class AltBeaconBeaconScannerConfig(
    val beaconTypes: Set<BeaconType>,
    val foregroundScanPeriod: Long,
    val foregroundBetweenScanPeriod: Long,
    val backgroundScanPeriod: Long,
    val backgroundBetweenScanPeriod: Long,
    val monitoringRegion: Region,
    val rangingRegion: Region,
    val foregroundServiceNotification: Notification?,
    val foregroundServiceNotificationId: Int,
) {

    val mappers: List<BeaconMapper> by lazy {
        beaconTypes.map {
            when (it) {
                BeaconType.EddystoneEid -> EddystoneEidMapper()
                BeaconType.EddystoneUic -> EddystoneUicMapper()
                BeaconType.IBeacon -> IBeaconMapper()
                BeaconType.KontaktSecureProfile -> KontaktSecureProfileMapper()
                BeaconType.WavePointerConnectable -> WavePointerConnectableMapper()
            }
        }
    }

    val parsers: List<BeaconParser> by lazy {
        beaconTypes.map {
            when (it) {
                BeaconType.EddystoneEid -> EddystoneEidParser()
                BeaconType.EddystoneUic -> EddystoneUicParser()
                BeaconType.IBeacon -> IBeaconParser()
                BeaconType.KontaktSecureProfile -> KontaktSecureProfileParser()
                BeaconType.WavePointerConnectable -> WavePointerConnectableParser()
            }
        }
    }
}
