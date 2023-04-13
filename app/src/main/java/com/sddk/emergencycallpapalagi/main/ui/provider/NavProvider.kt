package com.sddk.emergencycallpapalagi.main.ui.provider

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.navigation.NavController

class NavProvider(private val context: Context):PreviewParameterProvider<NavController> {
    override val values = sequenceOf(NavController(context))
}