package com.joaoflaviofreitas.lealfit.data.db.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseDTO(
    val name: String? = null,
    val image: String? = null,
    val observations: String? = null,
    val uid: Timestamp = Timestamp.now(),
) : Parcelable
