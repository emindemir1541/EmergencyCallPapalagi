package com.sddk.emergencycallpapalagi.main.data.sharedPreference

import android.content.Context
import com.sddk.emergencycallpapalagi.main.data.sharedPreference.SharedPreferenceBase


class SharedPreferenceRepository(private val mContext: Context) {

    private val base = SharedPreferenceBase(mContext)

    fun setInt(fileName: String, key: String, value: Int) {
        base.editor(fileName).putInt(key, value).apply()
    }

    fun getInt(fileName: String, key: String,defaultValue: Int = 0): Int {
        return base.preferences(fileName).getInt(key, defaultValue)
    }

    fun setLong(fileName: String, key: String, value: Long) {
        base.editor(fileName).putLong(key, value).apply()
    }

    fun getLong(fileName: String, key: String,defaultValue: Long = 0): Long {
        return base.preferences(fileName).getLong(key, defaultValue)
    }

    fun setFloat(fileName: String, key: String, value: Float) {
        base.editor(fileName).putFloat(key, value).apply()
    }

    fun getFloat(fileName: String, key: String,defaultValue: Float = 0f): Float {
        return base.preferences(fileName).getFloat(key, defaultValue)
    }

    fun setBoolean(fileName: String,key: String,value:Boolean){
         base.editor(fileName).putBoolean(key,value).apply()

    }

    fun getBoolean(fileName: String,key: String,defaultValue:Boolean = false):Boolean{
        return base.preferences(fileName).getBoolean(key,defaultValue)
    }

}
