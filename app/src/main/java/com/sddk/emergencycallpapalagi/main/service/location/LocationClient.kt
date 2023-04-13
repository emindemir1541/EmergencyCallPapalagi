package com.sddk.emergencycallpapalagi.main.service.location

import android.location.GnssNavigationMessage
import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {

    fun getLocationUpdates(interval:Long): Flow<Location>

    class LocationException(message: String):Exception()
}