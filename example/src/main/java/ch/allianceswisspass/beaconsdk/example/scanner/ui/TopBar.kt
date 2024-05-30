package ch.allianceswisspass.beaconsdk.example.scanner.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.example.widgets.AspTopAppBar

@Composable
fun TopBar() {
    AspTopAppBar(
        title = stringResource(id = R.string.scanner_title),
    )
}
