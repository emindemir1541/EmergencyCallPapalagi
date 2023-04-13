package com.sddk.emergencycallpapalagi.main.data.server

import android.content.Context
import com.sddk.emergencycallpapalagi.main.common.feedBack.addLog
import com.sddk.emergencycallpapalagi.main.common.feedBack.logTest
import com.sun.net.httpserver.HttpServer
import java.io.IOException
import java.io.OutputStream
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress
import java.net.InetSocketAddress


open class ServerCreator() {

    private var server: HttpServer? = null

    var port = 6060


    fun create(response: String, path: String, context: Context? = null): ServerCreator {

        if (server == null) {

            try {
                server = HttpServer.create(InetSocketAddress(port), 0)
            } catch (e: IOException) {
                throw e
            }

            server?.createContext(path) { exchange ->
                exchange.sendResponseHeaders(200, response.length.toLong())
                val os: OutputStream = exchange.responseBody
                os.write(response.toByteArray())
                os.close()
                addLog("Server", "Created Response: $response")
                addLog("Server", "path: ${server?.address}/$port/$path")
                /*
            if (context != null)
                addLog("Server", "path: ${SystemInfo(context).ipAddress}/$port/$path")
            else
                addLog("Server", "path: deviceIpAddress/$port/$path")

             */
            }
            start()
        }
        return this
    }


    fun start() {
        server?.start()
        addLog("Server", "Server Started")
    }

    fun stop() {
        server?.stop(0)
        server = null
        addLog("Server", "Server Stopped")
    }

}