package ch.allianceswisspass.beaconsdk.scanner.altbeacon

import android.content.Context
import ch.allianceswisspass.beaconsdk.scanner.BeaconScanner
import ch.allianceswisspass.beaconsdk.scanner.ScanResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.altbeacon.beacon.*

internal class AltBeaconBeaconScanner(
    context: Context,
    private val config: AltBeaconBeaconScannerConfig,
    private val onStart: (scanResults: Flow<List<ScanResult>>) -> Unit,
    private val onStop: () -> Unit,
) : BeaconScanner, MonitorNotifier, RangeNotifier {

    override val scanResults = MutableSharedFlow<List<ScanResult>>(replay = 1)

    private val beaconManager = BeaconManager.getInstanceForApplication(context)
    private var isMonitoring = false
    private var isRanging = false

    init {
        beaconManager.apply {
            beaconParsers.apply {
                clear()
                addAll(config.parsers)
            }
            setEnableScheduledScanJobs(false)
            foregroundScanPeriod = config.foregroundScanPeriod
            foregroundBetweenScanPeriod = config.foregroundBetweenScanPeriod
            backgroundScanPeriod = config.backgroundScanPeriod
            backgroundBetweenScanPeriod = config.backgroundBetweenScanPeriod
            if (config.foregroundServiceNotification != null) {
                enableForegroundServiceScanning(
                    config.foregroundServiceNotification,
                    config.foregroundServiceNotificationId
                )
            } else {
                disableForegroundServiceScanning()
            }
        }
    }

    override fun start() {
        startMonitoring()
        onStart.invoke(scanResults)
    }

    private fun startMonitoring() {
        if (isMonitoring) return
        beaconManager.addMonitorNotifier(this)
        beaconManager.startMonitoring(config.monitoringRegion)
        isMonitoring = true
    }

    private fun startRanging() {
        if (isRanging) return
        beaconManager.addRangeNotifier(this)
        beaconManager.startRangingBeacons(config.rangingRegion)
        isRanging = true
    }

    override fun stop() {
        onStop.invoke()
        stopMonitoring()
        stopRanging()
    }

    private fun stopMonitoring() {
        if (!isMonitoring) return
        beaconManager.stopMonitoring(config.monitoringRegion)
        beaconManager.removeMonitorNotifier(this)
        isMonitoring = false
    }

    private fun stopRanging() {
        if (!isRanging) return
        beaconManager.stopRangingBeacons(config.rangingRegion)
        beaconManager.removeRangeNotifier(this)
        isRanging = false
    }

    override fun didEnterRegion(region: Region?) {
        if (region != config.monitoringRegion) return
        startRanging()
    }

    override fun didExitRegion(region: Region?) {
        if (region != config.rangingRegion) return
        stopRanging()
    }

    override fun didDetermineStateForRegion(state: Int, region: Region?) {
        // ignore
    }

    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        if (region != config.rangingRegion) return
        val value = when {
            beacons.isNullOrEmpty() -> emptyList()
            else -> beacons.mapNotNull {
                for (mapper in config.mappers) {
                    val scanResult = mapper.map(it)
                    if (scanResult != null) return@mapNotNull scanResult
                }
                return@mapNotNull null
            }
        }
        scanResults.tryEmit(value)
    }
}
