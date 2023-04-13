package com.sddk.emergencycallpapalagi.main.ui.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.sddk.emergencycallpapalagi.main.ui.component.*
import com.sddk.emergencycallpapalagi.main.ui.theme.EmergencyCallTheme
import com.sddk.emergencycallpapalagi.main.ui.component.EmergencyButton
import com.sddk.emergencycallpapalagi.main.ui.component.GoogleMapView
import kotlinx.coroutines.*

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalUnitApi::class)
@Composable
fun MainPage(
    navController: NavController,
    location: MutableState<LatLng>,
    isButtonOpen: MutableState<Boolean>,
    onButtonClicked: () -> Unit,
) {
    EmergencyCallTheme {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        navController.enableOnBackPressed(false)

        val verticalSpace = 24.dp
        val padding = 24.dp
        val buttonSize = 170.dp
        val heightOfMap = screenHeight - (verticalSpace + padding * 2 + buttonSize)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {


            Column(modifier = Modifier
                .fillMaxWidth()
                .height(heightOfMap)) {
                GoogleMapView(location)
            }


            EmergencyButton(isButtonOpen, onClick = {
                    onButtonClicked()
            })


        }

    }

}









