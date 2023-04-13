package com.sddk.emergencycallpapalagi

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.sddk.emergencycallpapalagi.main.common.Helper.Helper
import com.sddk.emergencycallpapalagi.main.common.feedBack.logTest
import com.sddk.emergencycallpapalagi.main.common.security.AESEncryption
import com.sddk.emergencycallpapalagi.main.common.util.Resource
import com.sddk.emergencycallpapalagi.main.common.util.Screen
import com.sddk.emergencycallpapalagi.main.service.location.DefaultLocationClient
import com.sddk.emergencycallpapalagi.main.service.location.LocationClient
import com.sddk.emergencycallpapalagi.main.service.location.LocationHandler
import com.sddk.emergencycallpapalagi.main.service.location.LocationService
import com.sddk.emergencycallpapalagi.main.common.util.Progress
import com.sddk.emergencycallpapalagi.main.common.util.ServerUtil
import com.sddk.emergencycallpapalagi.main.ui.component.message
import com.sddk.emergencycallpapalagi.main.ui.page.Navigation
import com.sddk.emergencycallpapalagi.main.data.firebase.userrepository.UserViewModel
import com.sddk.emergencycallpapalagi.main.data.server.FakeLocationServer
import com.sddk.emergencycallpapalagi.main.data.server.LocationServer
import com.sddk.emergencycallpapalagi.main.data.server.ServerCreator
import com.sddk.emergencycallpapalagi.main.data.sharedPreference.Settings
import com.sddk.emergencycallpapalagi.main.data.viewmodel.MainViewModel
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationServiceIntent: Intent
    private lateinit var locationClient: LocationClient
    private var gpsStatus = false
    private lateinit var screen: MutableState<Screen>
    private lateinit var progress: MutableState<String>
    private lateinit var location: MutableState<LatLng>
    private var coroutineScope: CoroutineScope? = null
    private lateinit var buttonIsServiceOpen: MutableState<Boolean>
    private val mainViewModel: MainViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var server: FakeLocationServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepVisibleCondition {
                mainViewModel.isLoading.value
            }
        }

        window.navigationBarColor = resources.getColor(R.color.green10)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        locationServiceIntent = Intent(applicationContext, LocationService::class.java)
        locationClient = DefaultLocationClient(applicationContext, LocationServices.getFusedLocationProviderClient(applicationContext))
        userViewModel.getUser(this)
        server = FakeLocationServer()


        setContent {

            progress = remember { mutableStateOf(Progress.NOT_STARTED) }
            location = remember { mutableStateOf(LatLng(38.5136004, 27.0317419)) }
            //location = remember { mutableStateOf(LatLng(0.0, 0.0)) }
            coroutineScope = rememberCoroutineScope()
            buttonIsServiceOpen = remember { mutableStateOf(LocationService.isServiceOpen) }
            screen = remember { mutableStateOf(if (Settings(this).firstStart()) Screen.StartScreen else Screen.MainScreen) }

            userViewModel.user.observe(this) {
                when (it) {
                    is Resource.Loading -> mainViewModel._isLoading.value = true
                    is Resource.Success -> {
                        screen.value = Screen.MainScreen
                        mainViewModel._isLoading.value = false
                    }
                    is Resource.Error -> {
                        screen.value = Screen.RegisterScreen
                        mainViewModel._isLoading.value = false
                    }
                }
            }

            Navigation(screen = screen, location = location, isButtonOpen = buttonIsServiceOpen, userViewModel = userViewModel) {
                if (LocationHandler().checkConnections(this)) {
                    setService()
                }
            }

            getLocation()


        }


        mainViewModel._isLoading.value = false


    }

    override fun onResume() {
        super.onResume()
        gpsStatus = Helper.isGpsOpen(this)
        LocationHandler().checkConnections(this)
        userViewModel.getUser(this)


    }

    private fun setService() {
        LocationHandler().checkConnections(this)
        locationServiceIntent.apply {
            if (!LocationService.isServiceOpen) {
                //firebaseUserRepository.setUser(user)

                gpsStatus = Helper.isGpsOpen(this@MainActivity)
                if (gpsStatus) {
                    action = LocationService.ACTION_START
                    startService(this)
                    //    server.start(("${LocationService.currentLatitude},${LocationService.currentLongitude}"))
                    buttonIsServiceOpen.value = true
                    message("Tracking Started")
                    LocationService.isServiceOpen = true
                }
            }
            else {
                action = LocationService.ACTION_STOP
                LocationService.isServiceOpen = false
                stopService(this)
                //   server.stop()
                buttonIsServiceOpen.value = false
                //coroutineScope.cancel()
                message("Tracking Stopped")
            }
        }
    }


    private val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                message(getString(R.string.permission_granted))
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                message(getString(R.string.permission_granted))
            }
            else -> {
                permissionDeniedAlert()
                message(getString(R.string.location_permission_denied))
            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun getLocation() {
        coroutineScope?.launch(newSingleThreadContext("locationContext")) {
            while (true) {
                if (LocationService.isServiceOpen) {
                    delay(1000)
                    // server.stop()
                    //server.start("${LocationService.currentLatitude}, ${LocationService.currentLongitude}")
                    location.value = LatLng(LocationService.currentLatitude, LocationService.currentLongitude)
                    delay(2000)
                }
            }
        }
    }


    private fun permissionDeniedAlert() {
        /* MaterialAlertDialogBuilder(this).setTitle("Permission Denied").setMessage("Please give permission to location from settings, otherwise the app can't work").setPositiveButton("Get Permission") { dialog, which ->
         }.setNegativeButton("Exit") { dialog, which ->
             exitApp()
         }.show()*/

        /*   setContent{
           AlertDialog(title = "Permission Denied", message = "Please give permission to location from settings, otherwise the app can't work", positiveButton = {
               locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
           }, negativeButton = { exitApp() })
           }*/

    }


}


