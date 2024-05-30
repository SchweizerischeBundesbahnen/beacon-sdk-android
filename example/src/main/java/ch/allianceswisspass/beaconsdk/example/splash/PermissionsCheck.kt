@file:OptIn(ExperimentalPermissionsApi::class)

package ch.allianceswisspass.beaconsdk.example.splash

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ch.allianceswisspass.beaconsdk.example.R
import com.google.accompanist.permissions.*

@Composable
fun PermissionsCheck(
    content: @Composable () -> Unit,
) {
    var permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    // Add the ACCESS_BACKGROUND_LOCATION permission only on Android 10 (API level 29). On Android 11 (API level 30)
    // or higher, it's mandatory that the app performs incremental requests for location permissions, asking for
    // foreground location access and then background location access.
    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
        permissions = permissions.plus(
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        permissions = permissions.plus(
            listOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
            ),
        )
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissions = permissions.plus(
            Manifest.permission.POST_NOTIFICATIONS
        )
    }
    val permissionsState = rememberMultiplePermissionsState(
        permissions = permissions,
    )
    when {
        permissionsState.allPermissionsGranted -> Granted(content)
        permissionsState.shouldShowRationale -> Rationale(permissionsState)
        else -> Denied(permissionsState)
    }
}

//

@Composable
private fun Granted(content: @Composable () -> Unit) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        AccessBackgroundLocationPermissionCheck(content)
    } else {
        content()
    }
}

@Composable
private fun Rationale(permissionsState: MultiplePermissionsState) {
    Column {
        Text(
            text = stringResource(id = R.string.permissions),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = stringResource(id = R.string.permissions_rationale),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
        PermissionsStateList(permissionsState)
        RequestPermissionsButton(permissionsState)
    }
}

@Composable
private fun Denied(permissionsState: MultiplePermissionsState) {
    Column {
        Text(
            text = stringResource(id = R.string.permissions_denied),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = stringResource(id = R.string.permissions_denied_message),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
        PermissionsStateList(permissionsState)
        OpenSettingsButton(LocalContext.current)
    }
}

//

@Composable
private fun ColumnScope.PermissionsStateList(permissionsState: MultiplePermissionsState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
    ) {
        items(permissionsState.permissions) {
            PermissionStateListItem(it)
        }
    }
}

@Composable
private fun PermissionStateListItem(permissionState: PermissionState) {
    val text = permissionState.permission.removePrefix("android.permission.")
    ListItem(
        headlineContent = {
            Text(text = text)
        },
        leadingContent = {
            Checkbox(
                checked = permissionState.status.isGranted,
                onCheckedChange = null,
            )
        },
    )
}

@Composable
private fun RequestPermissionsButton(permissionsState: MultiplePermissionsState) {
    Button(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        onClick = {
            permissionsState.launchMultiplePermissionRequest()
        },
    ) {
        Text(
            text = stringResource(id = R.string.grant_permissions),
        )
    }
}

@Composable
private fun OpenSettingsButton(context: Context) {
    Button(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        onClick = {
            val appUri = Uri.parse("package:${context.packageName}")
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.setData(appUri)
            context.startActivity(intent)
        },
    ) {
        Text(
            text = stringResource(id = R.string.settings),
        )
    }
}
