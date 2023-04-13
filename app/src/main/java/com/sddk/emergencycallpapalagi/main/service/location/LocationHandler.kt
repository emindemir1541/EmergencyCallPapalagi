package com.sddk.emergencycallpapalagi.main.service.location

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import com.sddk.emergencycallpapalagi.main.common.Helper.Helper
import com.sddk.emergencycallpapalagi.main.common.Helper.Panels
import com.sddk.emergencycallpapalagi.main.common.feedBack.addLog

class LocationHandler() {

     fun locationPermissionRequest(activity: ActivityResultCaller,onPermissionDenied:()->Unit = {}) = activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                addLog("Location Permission","Permission Granted")
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                addLog("Location Permission","Permission Granted")
            }
            else -> {
                onPermissionDenied()
            }
        }
    }

    fun checkConnections(context: Context): Boolean {
        val panel = Panels(context)
        if (!Helper.isGpsOpen(context)) {
            panel.gps()
            return false
        }
        if (!Helper.isOnline(context)) {
            panel.internet()
            return false
        }
        return true
    }




}