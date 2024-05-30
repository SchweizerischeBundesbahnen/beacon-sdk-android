package ch.allianceswisspass.beaconsdk.example.scanner.ui.kontakt

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.stringResource
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.scanner.SectionState
import ch.allianceswisspass.beaconsdk.example.scanner.ui.SectionHeader
import ch.allianceswisspass.beaconsdk.scanner.ScanResult


@Suppress("FunctionName")
fun LazyListScope.KontaktSecureProfileSection(
    scanResults: List<ScanResult>,
    sectionState: SectionState,
    onUpdateSectionState: (state: SectionState) -> Unit,
) {
    item {
        SectionHeader(
            text = stringResource(id = R.string.kontakt_secure_profile),
            itemCount = scanResults.size,
            sectionState = sectionState,
            onUpdateSectionState = onUpdateSectionState,
        )
    }
    if (sectionState == SectionState.Expanded) {
        items(scanResults) {
            KontaktSecureProfileCard(scanResult = it)
        }
    }
}
