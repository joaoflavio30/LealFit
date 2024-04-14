package com.joaoflaviofreitas.lealfit.ui.login

data class SignupState(
    val success: Boolean = false,
    val error: Exception? = null,
    val loading: Boolean = false,
    val notConnection: Boolean = true
)
