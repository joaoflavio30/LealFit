package com.joaoflaviofreitas.lealfit.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Workout(
    var uid: String? = null,
    val name: String,
    val date: String,
    val description: String? = null,
    val exercises: MutableList<Exercise>? = null
): Parcelable
