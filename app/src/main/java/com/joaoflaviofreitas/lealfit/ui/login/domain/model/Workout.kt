package com.joaoflaviofreitas.lealfit.ui.login.domain.model

data class Workout(
    val name: String,
    val date: String,
    val description: String,
    val exercises: List<Exercise>
)
