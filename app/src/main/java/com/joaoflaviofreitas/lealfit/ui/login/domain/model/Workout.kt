package com.joaoflaviofreitas.lealfit.ui.login.domain.model

data class Workout(
    val uid: String? = null,
    val name: String,
    val date: String,
    val description: String? = null,
    val exercises: List<Exercise>? = null
)
