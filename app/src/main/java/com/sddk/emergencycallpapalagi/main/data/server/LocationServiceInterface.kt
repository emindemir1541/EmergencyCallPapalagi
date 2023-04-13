package com.sddk.emergencycallpapalagi.main.data.server

import android.content.Context

interface LocationServiceInterface {
    fun start(location:String,context:Context? = null)

    fun restart(location:String,context:Context? = null)

    fun stop()
}