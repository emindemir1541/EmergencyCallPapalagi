package com.sddk.emergencycallpapalagi.main.data.firebase.adminuserrepository

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sddk.emergencycallpapalagi.main.common.Helper.Helper
import com.sddk.emergencycallpapalagi.main.common.util.Resource
import com.sddk.emergencycallpapalagi.main.common.util.FirebaseModule
import com.sddk.emergencycallpapalagi.main.common.util.FirebasePaths
import com.sddk.emergencycallpapalagi.main.common.util.User
import com.sddk.emergencycallpapalagi.main.data.firebase.userrepository.UserRepository

class AdminUserRepositoryImpl(private val context: Context) : UserRepository {

    private val firebase = FirebaseModule.provideFireStoreInstance()

    private val deviceId = Helper.getDeviceId(context)
    private val selectedUserPath = firebase.child(FirebasePaths.drone).child(FirebasePaths.selectedUser).child(deviceId)


    override fun getUser(result: (Resource<User>) -> Unit) {
        selectedUserPath.get().addOnSuccessListener {
            val userData = it.getValue(User::class.java)
            if (userData != null) {
                val user: User = userData
                result.invoke(Resource.Success(user))
            }else{
                result.invoke(Resource.Error("Null Error"))
            }
        }.addOnFailureListener {
            result.invoke(Resource.Error(it.localizedMessage))
        }
    }

    override fun addUser(user: User,encryption:Boolean, result: (Resource<Void>) -> Unit) {
        selectedUserPath.setValue(user).addOnSuccessListener {
            result.invoke(Resource.Success(it))
        }.addOnFailureListener {
            result.invoke(Resource.Error(it.localizedMessage))
        }

    }

    override fun userListener(userItemPath: String, userListener: (String) -> Unit) {
        selectedUserPath.child(userItemPath).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               userListener.invoke(snapshot.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}