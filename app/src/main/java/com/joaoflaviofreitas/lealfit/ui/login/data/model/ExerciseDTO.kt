package com.joaoflaviofreitas.lealfit.ui.login.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseDTO(
    val name: String? = null,
    val image: String? = null,
    val observations: String? = null
) : Parcelable
