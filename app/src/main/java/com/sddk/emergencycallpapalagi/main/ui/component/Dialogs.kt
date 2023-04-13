package com.sddk.emergencycallpapalagi.main.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sddk.emergencycallpapalagi.R
import com.sddk.emergencycallpapalagi.main.common.util.Progress
import com.sddk.emergencycallpapalagi.main.ui.theme.EmergencyCallTheme



@Composable
fun PermissionDialog(
    permission: String,
    permissionDescription: String,
    isPermanentlyDeclined: Boolean,
    dismissButton: () -> Unit,
    confirmButton: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    EmergencyCallTheme() {

    AlertDialog(
        title = { Text(text = permission)},
        text = { Text(text = permissionDescription)},
        onDismissRequest = {},
        modifier = modifier,
        dismissButton = {
            Button(onClick = dismissButton) {
            Text(text = stringResource(id = R.string.exit_app))
            }
        },
        confirmButton = {
            Button(onClick = confirmButton) {
            Text(text = stringResource(id = R.string.get_permission))
            }
        }

    )
    }

}



@Composable
fun ProgressingIndicator(modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.background) {
    CircularProgressIndicator(modifier = modifier, strokeWidth = Dp(value = 4F), color = color)
}

@Composable
fun ProgressingFullScreen(progress: MutableState<String>) {
    if (progress.value == Progress.PROGRESSING)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,


            ) {
            ProgressingIndicator(modifier = Modifier.padding(16.dp))
        }
}


