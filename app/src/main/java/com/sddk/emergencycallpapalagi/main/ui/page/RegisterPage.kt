package com.sddk.emergencycallpapalagi.main.ui.page

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sddk.emergencycallpapalagi.main.common.Helper.Helper
import com.sddk.emergencycallpapalagi.main.common.Helper.isEmpty
import com.sddk.emergencycallpapalagi.R
import com.sddk.emergencycallpapalagi.main.common.Helper.Error
import com.sddk.emergencycallpapalagi.main.common.util.Resource
import com.sddk.emergencycallpapalagi.main.common.util.Screen
import com.sddk.emergencycallpapalagi.main.ui.theme.EmergencyCallTheme
import com.sddk.emergencycallpapalagi.main.common.util.User
import com.sddk.emergencycallpapalagi.main.data.firebase.userrepository.UserViewModel
import com.sddk.emergencycallpapalagi.main.ui.component.OutlinedTextBox
import com.sddk.emergencycallpapalagi.main.common.util.Progress
import com.sddk.emergencycallpapalagi.main.ui.component.ProgressingIndicator


@Composable
fun RegisterPage(navController: NavController = NavController(LocalContext.current), userViewModel: UserViewModel) {

    EmergencyCallTheme {

        val context = LocalContext.current
        val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)


        val saveProgressing = remember { mutableStateOf(Progress.NOT_STARTED) }

        val name = remember { mutableStateOf(TextFieldValue("")) }
        val surname = remember { mutableStateOf(TextFieldValue("")) }

        val age = remember { mutableStateOf(TextFieldValue("")) }
        val phoneNo = remember { mutableStateOf(TextFieldValue("")) }

        val nameError = remember { mutableStateOf(false) }
        val surnameError = remember { mutableStateOf(false) }
        val ageError = remember { mutableStateOf(false) }
        val phoneError = remember { mutableStateOf(false) }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {


            //Title
            Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

                Row(modifier = Modifier.padding(top = 70.dp)) {

                    Text(text = stringResource(R.string.papalagi), fontSize = 25.sp, color = MaterialTheme.colorScheme.primary)

                }
                Text(text = stringResource(id = R.string.register_for_start), fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
            }

            //Text Field Rows
            Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextBox(label = stringResource(id = R.string.name), text = name, isError = nameError)
                OutlinedTextBox(label = stringResource(id = R.string.surname), text = surname, isError = surnameError)
                OutlinedTextBox(label = stringResource(id = R.string.age), text = age, KeyboardType.Number, ageError)
                OutlinedTextBox(label = stringResource(id = R.string.phone), text = phoneNo, KeyboardType.Phone, phoneError)
            }

            if (saveProgressing.value == Progress.FINISHED) {
                saveProgressing.value = Progress.NOT_STARTED
                navController.navigate(Screen.MainScreen.route)
            }

            fun noError(): Boolean {
                return when (Error.EMPTY) {
                    name.value.isEmpty(nameError) -> false
                    surname.value.isEmpty(surnameError) -> false
                    age.value.isEmpty(ageError) -> false
                    phoneNo.value.isEmpty(phoneError) -> false
                    else -> true
                }
            }

            Button(
                onClick = {
                    saveProgressing.value = Progress.PROGRESSING
                    if (noError()) {
                        val user = User(Helper.getDeviceId(context), name.value.text, surname.value.text, age.value.text.toIntOrNull(), phoneNo.value.text.toLongOrNull())
                        userViewModel.addUser(context, user)
                        userViewModel.addUser.observe(lifecycleOwner.value) {
                            when (it) {
                                is Resource.Error -> {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                    saveProgressing.value = Progress.NOT_STARTED
                                }
                                is Resource.Success -> {
                                    saveProgressing.value = Progress.FINISHED
                                }
                                is Resource.Loading -> saveProgressing.value = Progress.PROGRESSING
                            }
                        }
                    }
                    else {
                        saveProgressing.value = Progress.NOT_STARTED
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clickable(saveProgressing.value != Progress.PROGRESSING, "click", onClick = {}),
            ) {
                if (saveProgressing.value == Progress.NOT_STARTED)
                    Text(text = stringResource(id = R.string.sign_up))
                else if (saveProgressing.value == Progress.PROGRESSING)
                    ProgressingIndicator()


            }


        }
    }

}


