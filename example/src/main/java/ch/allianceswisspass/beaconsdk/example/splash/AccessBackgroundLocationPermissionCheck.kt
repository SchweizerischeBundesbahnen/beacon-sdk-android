@file:OptIn(ExperimentalPermissionsApi::class)

package ch.allianceswisspass.beaconsdk.example.splash

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ch.allianceswisspass.beaconsdk.example.R
import com.google.accompanist.permissions.*

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun AccessBackgroundLocationPermissionCheck(
    content: @Composable () -> Unit,
) {
    val permission = Manifest.permission.ACCESS_BACKGROUND_LOCATION
    val permissionState = rememberPermissionState(permission)
    when {
        permissionState.status.isGranted -> content()
        permissionState.status.shouldShowRationale -> Rationale(permissionState)
        else -> content()
    }
}

//

@RequiresApi(Build.VERSION_CODES.R)
@Composable
private fun Rationale(permissionState: PermissionState) {
    val context = LocalContext.current
    val settingLabel = context.packageManager.backgroundPermissionOptionLabel.toString()
    Column {
        Text(
            text = stringResource(id = R.string.background_location_access),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            buildAnnotatedString {
                append(stringResource(id = R.string.background_location_access_rationale_1))
                append(" ")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        fontWeight = FontWeight.Bold,
                        background = MaterialTheme.colorScheme.inverseSurface
                    )
                ) {
                    append(" ")
                    append(settingLabel)
                    append(" ")
                }
                append(" ")
                append(stringResource(id = R.string.background_location_access_rationale_2))
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
        )
        SkipButton()
        RequestPermissionButton(permissionState)
    }
}

//

@Composable
private fun SkipButton() {
    OutlinedButton(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth(),
        onClick = {},
    ) {
        Text(
            text = stringResource(id = R.string.skip),
        )
    }
}

@Composable
private fun RequestPermissionButton(permissionState: PermissionState) {
    Button(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        onClick = {
            permissionState.launchPermissionRequest()
        },
    ) {
        Text(
            text = stringResource(id = R.string.grant_permission),
        )
    }
}
