package com.sddk.emergencycallpapalagi.main.service.location

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.sddk.emergencycallpapalagi.main.common.Helper.DateHelper
import com.sddk.emergencycallpapalagi.main.common.feedBack.addLog
import com.sddk.emergencycallpapalagi.main.common.util.UserSituation
import com.google.android.gms.location.LocationServices
import com.sddk.emergencycallpapalagi.main.common.util.Resource
import com.sddk.emergencycallpapalagi.main.common.feedBack.logTest
import com.sddk.emergencycallpapalagi.main.data.firebase.userrepository.UserRepository
import com.sddk.emergencycallpapalagi.main.data.firebase.userrepository.UserRepositoryImpl
import com.sddk.emergencycallpapalagi.R
import com.sddk.emergencycallpapalagi.main.common.util.FirebasePaths
import com.sddk.emergencycallpapalagi.main.data.firebase.adminuserrepository.AdminUserRepositoryImpl
import com.sddk.emergencycallpapalagi.main.data.server.LocationServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private val server = LocationServer()
    private lateinit var userRepository: UserRepository
    private var loop = 1

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        locationClient = DefaultLocationClient(applicationContext, LocationServices.getFusedLocationProviderClient(applicationContext))
        // userRepository = AdminUserRepositoryImpl(applicationContext)
        userRepository = UserRepositoryImpl(applicationContext)
        // TODO: change to userrepository
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        setUserSituationListener(intent)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setUserSituationListener(intent: Intent?) {
        /*    userRepository.userListener(FirebasePaths.situation) { situation ->
                if (situation == UserSituation.SAFE) {
                    stop()
                    intent?.action = ACTION_STOP
                }
            }*/
        // TODO: change this place
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    private fun start() {

        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking location..")
            .setContentText("Location: Couldn't connect to GPS")
            .setSmallIcon(R.drawable.papalagi_logo_green10)
            .setOngoing(true)


        userRepository.getUser {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            when (it) {
                is Resource.Success -> {
                    logTest("LocationService", "Success: ${it.data}")
                    val newUser = it.data

                    if (newUser != null) {
                        locationClient
                            .getLocationUpdates(3000L)
                            .catch { e -> e.printStackTrace() }
                            .onEach { location ->

                                val latitude = location.latitude.toString()
                                val longitude = location.longitude.toString()
                                val locationString = "$latitude, $longitude"
                                val updateNotification = notification.setContentText("Location: ($locationString)")

                                addLog("Location", "($locationString)")

                                currentLatitude = location.latitude
                                currentLongitude = location.longitude

                                newUser.latitude = location.latitude
                                newUser.longitude = location.longitude
                                newUser.locationTime = DateHelper().currentTime
                                newUser.situation = UserSituation.IN_DANGER

                                // FirebaseUserRepository().setUser(newUser)

                                userRepository.addUser(newUser) {}

                                serverStartCase {
                                    server.start(locationString)
                                }


                                notificationManager.notify(1, updateNotification.build())
                            }
                            .launchIn(serviceScope)

                        startForeground(1, notification.build())
                    }
                }
                is Resource.Loading -> {
                    logTest("LocationService", "Loading Data")
                    val updateNotification = notification.setContentText("Connecting to server")
                    notificationManager.notify(1, updateNotification.build())
                }
                is Resource.Error -> {
                    val updateNotification = notification.setContentText("Error connecting server")
                    notificationManager.notify(1, updateNotification.build())
                    logTest("LocationSerivce", "Error: ${it.message}")
                }
            }
        }


    }

    private fun serverStartCase(case: () -> Unit) {
        val selectedLoop = 2
        if (loop == selectedLoop)
            case()
        if (loop < selectedLoop)
            loop++
    }

    private fun stop() {
        server.stop()
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        server.stop()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_START"

        var currentLatitude = 0.0
        var currentLongitude = 0.0


        var isServiceOpen = false
    }

}

