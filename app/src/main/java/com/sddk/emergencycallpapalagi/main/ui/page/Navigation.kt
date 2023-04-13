package com.sddk.emergencycallpapalagi.main.ui.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.LatLng
import com.sddk.emergencycallpapalagi.main.common.util.Screen
import com.sddk.emergencycallpapalagi.main.common.util.User
import com.sddk.emergencycallpapalagi.main.data.firebase.userrepository.UserViewModel
import com.sddk.emergencycallpapalagi.main.data.sharedPreference.Settings
import com.sddk.emergencycallpapalagi.main.data.viewmodel.MainViewModel

@Composable
fun Navigation(screen: MutableState<Screen>,userViewModel: UserViewModel,location:MutableState<LatLng>,isButtonOpen:MutableState<Boolean>,onButtonClicked:()->Unit){


    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = if(Settings(LocalContext.current).firstStart()) Screen.StartScreen.route else screen.value.route){

        composable(route = Screen.MainScreen.route){
            MainPage( navController,location,isButtonOpen,onButtonClicked)
        }

        composable(route = Screen.RegisterScreen.route){
            RegisterPage(navController,userViewModel)
        }
        composable(route = Screen.StartScreen.route){
            StartPage(navController)
        }
      /*  composable(route = Screen.SplashScreen.route){
            SplashPage(navController)
        }*/
    }
}