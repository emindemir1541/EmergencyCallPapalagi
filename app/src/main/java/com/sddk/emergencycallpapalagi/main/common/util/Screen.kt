package com.sddk.emergencycallpapalagi.main.common.util

import android.content.Context
import com.sddk.emergencycallpapalagi.main.data.sharedPreference.Settings

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object RegisterScreen : Screen("register_screen")
    object StartScreen : Screen("start_screen")
    object SplashScreen : Screen("splash_screen")

}
