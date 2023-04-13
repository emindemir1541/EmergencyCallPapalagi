package com.sddk.emergencycallpapalagi.main.ui.component

import android.content.Context
import android.widget.Toast
import com.sddk.emergencycallpapalagi.main.common.feedBack.addLog

fun Context.message(text:String){
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    addLog("message",text)
}