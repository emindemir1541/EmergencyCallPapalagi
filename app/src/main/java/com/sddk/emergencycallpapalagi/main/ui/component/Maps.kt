package com.sddk.emergencycallpapalagi.main.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*

@Composable
fun GoogleMapView(
    location: MutableState<LatLng>,
    modifier: Modifier = Modifier,
) {

    val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }

    val cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(location.value, 10f) }


    Box(modifier = modifier
        .border(2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(64.dp))
        .clip(RoundedCornerShape(64.dp))
    ) {

        GoogleMap(cameraPositionState = cameraPositionState, uiSettings = uiSettings, properties = properties) {
            Marker(title = "Final Location", state = MarkerState(position = location.value), snippet = "Your Location")
        }
    }

}