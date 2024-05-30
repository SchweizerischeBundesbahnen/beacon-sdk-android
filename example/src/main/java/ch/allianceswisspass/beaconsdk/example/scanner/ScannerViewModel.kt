package ch.allianceswisspass.beaconsdk.example.scanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import ch.allianceswisspass.beaconsdk.scanner.BeaconScanner
import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val scanner: BeaconScanner,
) : ViewModel() {

    val sectionStates = MutableLiveData(
        mapOf(
            BeaconType.IBeacon to SectionState.Expanded,
            BeaconType.EddystoneEid to SectionState.Expanded,
            BeaconType.EddystoneUic to SectionState.Expanded,
            BeaconType.KontaktSecureProfile to SectionState.Expanded,
            BeaconType.WavePointerConnectable to SectionState.Expanded,
        )
    )

    val isScanning = MutableLiveData(false)
    val scanResults get() = scanner.scanResults.asLiveData()

    init {
        Timber.d(message = "Init view model")
        @Suppress("OPT_IN_USAGE")
        GlobalScope.launch {
            scanner.scanResults.collect {
                Timber.v("Received ${it.size} scan results")
            }
        }
    }

    fun updateSectionState(beaconType: BeaconType, state: SectionState) {
        Timber.d("Update section state: $beaconType -> $state")
        val states = sectionStates.value!!.toMutableMap()
        states[beaconType] = state
        sectionStates.value = states
    }

    fun startScanning() {
        Timber.d(message = "Start scanning")
        scanner.start()
        isScanning.value = true
    }

    fun stopScanning() {
        Timber.d(message = "Stop scanning")
        scanner.stop()
        isScanning.value = false
    }

    override fun onCleared() {
        Timber.d(message = "Clear view model")
        stopScanning()
    }
}
