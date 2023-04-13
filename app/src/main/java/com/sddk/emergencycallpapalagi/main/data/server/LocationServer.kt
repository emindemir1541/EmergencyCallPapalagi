package com.sddk.emergencycallpapalagi.main.data.server

import android.content.Context
import com.sddk.emergencycallpapalagi.main.common.util.ServerUtil

class LocationServer() : LocationServiceInterface {

    private val creator = ServerCreator()

    override fun start(location: String, context: Context?) {
        creator.create(location, ServerUtil.locationResponsePath, context)
    }

    override fun restart(location: String, context: Context?) {
        creator.stop()
        creator.create(location, ServerUtil.locationResponsePath, context)
    }

    override fun stop() {
        creator.stop()
    }

}