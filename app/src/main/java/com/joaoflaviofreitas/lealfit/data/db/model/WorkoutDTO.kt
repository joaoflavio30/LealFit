package com.joaoflaviofreitas.lealfit.data.db.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutDTO(
    var uid: String? = null,
    val name: String? = null,
    val description: String? = null,
    val date: Timestamp? = null,
    val exercises: List<ExerciseDTO>? = null
) : Parcelable
