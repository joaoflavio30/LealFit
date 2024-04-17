package com.joaoflaviofreitas.lealfit.domain.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val name: String,
    var image: String? = null,
    val observations: String,
    val uid: Timestamp = Timestamp.now(),
): Parcelable
