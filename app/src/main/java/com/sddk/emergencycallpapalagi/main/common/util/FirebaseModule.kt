package com.sddk.emergencycallpapalagi.main.common.util

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

object FirebaseModule {


    fun provideFireStoreInstance():DatabaseReference{
        return FirebaseDatabase.getInstance().getReference(FirebasePaths.dataStore)
    }
}