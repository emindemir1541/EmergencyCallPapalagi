package com.sddk.emergencycallpapalagi.main.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextBox(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, color = MaterialTheme.colorScheme.primary, fontSize = 20.sp)
}

@Composable
fun TextLabel(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, color = Color.Black, fontSize = 23.sp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextBox(label: String, text: MutableState<TextFieldValue>,  keyboardType: KeyboardType = KeyboardType.Text,isError: MutableState<Boolean> = remember { mutableStateOf(false) }) {
    OutlinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
        isError = isError.value,
        colors =TextFieldDefaults.outlinedTextFieldColors(),
        trailingIcon = {if (isError.value) Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)})
}