package com.sddk.emergencycallpapalagi.main.common.feedBack

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.test(data: Any?){
        Toast.makeText(this,"TEST--> ${data.toString()}", Toast.LENGTH_LONG).show()
        Log.e("TEST", data.toString())
}

fun Context.test(title:String,data: Any?){
        Toast.makeText(this,"TEST--> $title :: ${data.toString()}", Toast.LENGTH_LONG).show()
        Log.e("TEST","$title --> ${data.toString()}")
}

fun logTest(data: Any?):Any?{
        Log.e("TEST", data.toString())
        return data
}
fun logTest(title: String,data: Any?):Any?{
        Log.e(title,"$title --> ${data.toString()}")
        return data
}

