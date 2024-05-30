package ch.allianceswisspass.beaconsdk.example.scanner.ui.eddystone.uic

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.stringResource
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.scanner.SectionState
import ch.allianceswisspass.beaconsdk.example.scanner.ui.SectionHeader
import ch.allianceswisspass.beaconsdk.scanner.ScanResult

@Suppress("FunctionName")
fun LazyListScope.EddystoneUicSection(
    scanResults: List<ScanResult>,
    sectionState: SectionState,
    onUpdateSectionState: (state: SectionState) -> Unit,
) {
    item {
        SectionHeader(
            text = stringResource(id = R.string.eddystone_uic),
            itemCount = scanResults.size,
            sectionState = sectionState,
            onUpdateSectionState = onUpdateSectionState,
        )
    }
    if (sectionState == SectionState.Expanded) {
        items(scanResults) {
            EddystoneUicCard(scanResult = it)
        }
    }
}
