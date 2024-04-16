package com.joaoflaviofreitas.lealfit.ui.login.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val name: String,
    var image: String? = null,
    val observations: String
): Parcelable
