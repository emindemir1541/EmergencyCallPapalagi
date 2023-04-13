package com.sddk.emergencycallpapalagi.main.data.sharedPreference

import android.content.Context

private const val SETTINGS = "SETTINGS"
private const val FIRST_START = "FIRST_START"

class Settings(private val context: Context) {


    //it saves data that app has started first time after downloaded
    fun setFirstStart(value: Boolean) {
        SharedPreferenceRepository(context).setBoolean(SETTINGS, FIRST_START, value)
    }

    fun firstStart() = SharedPreferenceRepository(context).getBoolean(SETTINGS, FIRST_START,true)

}