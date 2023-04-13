package com.sddk.emergencycallpapalagi.main.data.firebase.userrepository

import com.sddk.emergencycallpapalagi.main.common.util.Resource
import com.sddk.emergencycallpapalagi.main.common.util.User

interface UserRepository {

    fun getUser(result: (Resource<User>) -> Unit)

    fun addUser(user: User,encryption:Boolean = false,result:(Resource<Void>)->Unit)

    fun userListener(userItemPath:String,userListener:(String)->Unit)

}