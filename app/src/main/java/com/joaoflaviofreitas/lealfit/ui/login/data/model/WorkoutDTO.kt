package com.joaoflaviofreitas.lealfit.ui.login.data.model

import com.google.firebase.Timestamp

data class WorkoutDTO(
    val uid: String? = null,
    val name: String? = null,
    val description: String? = null,
    val date: Timestamp? = null,
    val exercises: List<ExerciseDTO>? = null
)
