package ch.allianceswisspass.beaconsdk.example.vehicles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import ch.allianceswisspass.beaconsdk.resolver.BeaconInfo
import ch.allianceswisspass.beaconsdk.resolver.BeaconResolver
import ch.allianceswisspass.beaconsdk.resolver.Vehicle
import ch.allianceswisspass.beaconsdk.scanner.BeaconScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VehiclesViewModel @Inject constructor(
    private val beaconScanner: BeaconScanner,
    private val beaconResolver: BeaconResolver,
) : ViewModel() {

    val isScanning = MutableLiveData(false)

    val vehicles by lazy {
        beaconInfos.map { data ->
            val result = mutableMapOf<Vehicle, List<BeaconInfo>>()
            data.forEach { entry ->
                val vehicle = entry.value.installationLocation.vehicle
                val beaconInfos = result[vehicle]?.toMutableSet() ?: mutableSetOf()
                beaconInfos.add(entry.value)
                result[vehicle] = beaconInfos.toList()
            }
            result.toMap()
        }
    }

    private val beaconInfos by lazy {
        val flow = beaconResolver.resolve(
            scanResults = beaconScanner.scanResults,
        )
        flow.asLiveData()
    }

    init {
        Timber.d(message = "Init view model")
    }

    fun startScanning() {
        Timber.d(message = "Start scanning")
        beaconScanner.start()
        isScanning.value = true
    }

    fun stopScanning() {
        Timber.d(message = "Stop scanning")
        beaconScanner.stop()
        isScanning.value = false
    }

    override fun onCleared() {
        Timber.d(message = "Clear view model")
        stopScanning()
    }
}
