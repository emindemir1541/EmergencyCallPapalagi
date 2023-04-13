package com.sddk.emergencycallpapalagi.main.common.Helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.text.format.Formatter
import com.sddk.emergencycallpapalagi.BuildConfig


@SuppressLint("HardwareIds")
class SystemInfo(private val context: Context){

    val packageName = context.packageName
    val deviceId = android.provider.Settings.Secure.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID)
    private val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val ipAddress = Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)

    fun device():MutableMap<String,String>{
        val device: MutableMap<String, String> = HashMap()
        device["brand"] = Build.BRAND
        device["Model"] = Build.MODEL
        device["ID"] = Build.ID
        device["hardware"] = Build.HARDWARE
        device["product"] = Build.PRODUCT
        device["serial"] = Build.SERIAL
        device["manufacture"] = Build.MANUFACTURER
        device["board"] = Build.BOARD
        device["host"] = Build.HOST
        device["fingerprint"] = Build.FINGERPRINT
        device["cpuAbi"] = Build.CPU_ABI

        return device
    }


    fun android():MutableMap<String,String>{
        val android: MutableMap<String, String> = HashMap()
        android["sdk"] = Build.VERSION.SDK_INT.toString()
        android["user"] = Build.USER
        android["userType"] = Build.TYPE
        android["incremental"] = Build.VERSION.INCREMENTAL
        android["version"] = Build.VERSION.RELEASE
        android["host"] = Build.HOST
        return android
    }



    fun app():MutableMap<String,String>{
        val app: MutableMap<String, String> = HashMap()
        app["versionCode"] = BuildConfig.VERSION_CODE.toString()
        app["versionName"] = BuildConfig.VERSION_NAME
        return app
    }

}







