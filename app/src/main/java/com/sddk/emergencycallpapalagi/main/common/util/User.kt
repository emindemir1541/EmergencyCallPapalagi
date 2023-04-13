package com.sddk.emergencycallpapalagi.main.common.util

import com.sddk.emergencycallpapalagi.main.common.security.AESEncryption

data class User(
    var deviceId: String? = null,
    var name: String? = null,
    var surname: String? = null,
    var age: Int? = null,
    var phoneNo: Long? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var locationTime: Long? = null,
    var situation: String? = null,
    var explanation: String? = null,
    var message: String? = null

) {

    fun encrypt(): User {

        deviceId = AESEncryption.encrypt(deviceId)
        name = AESEncryption.encrypt(name)
        surname = AESEncryption.encrypt(surname)
        age = AESEncryption.encrypt(age?.toString())?.toInt()
        phoneNo = AESEncryption.encrypt(phoneNo?.toString())?.toLong()
        latitude = AESEncryption.encrypt(latitude?.toString())?.toDouble()
        longitude = AESEncryption.encrypt(longitude?.toString())?.toDouble()
        locationTime = AESEncryption.encrypt(locationTime?.toString())?.toLong()
        situation = AESEncryption.encrypt(situation)
        explanation = AESEncryption.encrypt(explanation)
        message = AESEncryption.encrypt(message)

        return this
    }


}
