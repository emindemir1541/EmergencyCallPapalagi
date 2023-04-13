package com.sddk.emergencycallpapalagi.main.data.firebase.userrepository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sddk.emergencycallpapalagi.main.common.util.Resource
import com.sddk.emergencycallpapalagi.main.common.util.User

class UserViewModel() : ViewModel() {


    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>>
        get() = _user

    private val _addUser = MutableLiveData<Resource<Void>>()
    val addUser: LiveData<Resource<Void>>
        get() = _addUser

    fun getUser(context:Context) {
        val repository = UserRepositoryImpl(context)
        _user.value = Resource.Loading()
        repository.getUser { _user.value = it }
    }

    fun addUser(context: Context,user:User,encryption:Boolean = false){
        val repository = UserRepositoryImpl(context)
        _addUser.value = Resource.Loading()
        repository.addUser(user,encryption){_addUser.value=it}
    }

}