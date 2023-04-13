package com.sddk.emergencycallpapalagi.main.ui.page

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sddk.emergencycallpapalagi.main.data.sharedPreference.Settings
import kotlinx.coroutines.delay

/*@Composable
fun SplashPage(navController: NavController = NavController(LocalContext.current)) {

    val settings = Settings(LocalContext.current)
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(targetValue = if (startAnimation) 1f else 0f, animationSpec = tween(durationMillis = 3000))

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        //navController.navigate(if (settings.firstStart()) Screen.StartScreen.route else Screen.MainScreen.route)
    }
    SplashPageDesign(alphaAnimation.value)


}

@Composable
fun SplashPageDesign(alpha:Float) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .alpha(alpha),
        contentAlignment = Alignment.Center) {
        Icon(modifier = Modifier.size(60.dp), imageVector = Icons.Default.Email, contentDescription = "Logo icon", tint = Color.White)

    }
}*/
