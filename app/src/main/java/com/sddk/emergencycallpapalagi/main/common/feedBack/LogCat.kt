package com.sddk.emergencycallpapalagi.main.common.feedBack

import android.util.Log

fun addLog(data: Any?){
    Log.e("TEST", data.toString())
}

fun addLog(title: String,data: Any?,description:String = ""){
    Log.e(title,"$title --> ${data.toString()} --> $description")
}