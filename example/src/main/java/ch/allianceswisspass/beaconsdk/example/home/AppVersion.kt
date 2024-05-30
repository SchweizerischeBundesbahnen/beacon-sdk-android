package ch.allianceswisspass.beaconsdk.example.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ch.allianceswisspass.beaconsdk.example.BuildConfig
import ch.allianceswisspass.beaconsdk.example.R

@Composable
fun AppVersion() {
    Text(
        text = stringResource(
            id = R.string.app_version,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        ),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    )
}
