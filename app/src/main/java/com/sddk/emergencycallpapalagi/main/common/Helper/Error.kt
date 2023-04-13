package com.sddk.emergencycallpapalagi.main.common.Helper

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue

class Error {
    companion object{
        const val EMPTY="EMPTY"
        const val NO_ERROR ="NO_ERROR"
    }
}

fun TextFieldValue.isEmpty(isError:MutableState<Boolean>): String {
    return if (this.text.isEmpty()) {
        isError.value = true
        Error.EMPTY
    }
    else{
        isError.value = false
        Error.NO_ERROR
    }
}

