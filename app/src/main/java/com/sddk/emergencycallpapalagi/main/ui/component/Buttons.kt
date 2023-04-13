package com.sddk.emergencycallpapalagi.main.ui.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.*
import com.sddk.emergencycallpapalagi.main.ui.theme.EmergencyCallTheme

@Composable
fun EmergencyButton(open: MutableState<Boolean>, onClick: () -> Unit = {}, scope: () -> Unit = {}) {
    EmergencyCallTheme {
        val buttonSize = 170.dp
        val isAnimationOn = remember { mutableStateOf(false) }
        val sizeDp by animateDpAsState(targetValue = if (!isAnimationOn.value) 4.dp else buttonSize, tween(350)) {
            isAnimationOn.value = false
        }

        val modifier = Modifier
        Box(
            modifier = modifier
                .size(buttonSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .border(BorderStroke(sizeDp, MaterialTheme.colorScheme.primary), CircleShape)
                .clickable(enabled = !isAnimationOn.value, onClick = {
                    isAnimationOn.value = true
                    open.value = !open.value
                    onClick()
                })

        ) {

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                if (!isAnimationOn.value) {
                    if (!open.value)
                        TextBox(text = "Start")
                    else
                        TextBox(text = "Stop")
                    scope()
                }
            }
        }
    }
}

