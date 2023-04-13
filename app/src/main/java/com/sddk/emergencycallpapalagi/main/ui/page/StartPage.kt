package com.sddk.emergencycallpapalagi.main.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sddk.emergencycallpapalagi.R
import com.sddk.emergencycallpapalagi.main.common.util.Screen
import com.sddk.emergencycallpapalagi.main.data.sharedPreference.Settings
import com.sddk.emergencycallpapalagi.main.ui.theme.EmergencyCallTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartPage(navController: NavController = NavController(LocalContext.current)) {

    EmergencyCallTheme {

        val context = LocalContext.current
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val circleBoxSize = 140.dp
            val cardSize = screenHeight * (45) / 100


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardSize + circleBoxSize / 2)

            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier
                        .clip(RoundedCornerShape(bottomEnd = 60.dp, bottomStart = 60.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                        .height(cardSize)


                    )

                }


                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                    Row(modifier = Modifier
                        .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .size(circleBoxSize)
                                .border(2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(20.dp)),
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(2.dp),
                                painter = painterResource(id = if (!isSystemInDarkTheme()) R.drawable.papalagi_logo_green10 else R.drawable.papalagi_logo_yellow60),
                                contentScale = ContentScale.Fit,
                                contentDescription = "profile_ımage")
                        }

                    }
                }


            }

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(15.dp)) {
                Text(text = stringResource(R.string.emergency_call) , fontSize = 30.sp, color = MaterialTheme.colorScheme.primary)
                Text(text = stringResource(R.string.app_description), color = MaterialTheme.colorScheme.primary)
            }

            Button(onClick = {
                navController.navigate(Screen.RegisterScreen.route)
                Settings(context).setFirstStart(false)
            }) {
                Text(text = stringResource(R.string.get_started))
            }


            Row() {

            }
        }
    }

}

@Preview()
@Composable
private fun DefaultPreview(){
    StartPage()
}