package com.joaoflaviofreitas.lealfit.ui.login.domain

import android.net.Uri
import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import kotlinx.coroutines.flow.Flow

interface AddExerciseRepository {
    suspend fun createExercise(exercise: Exercise, workoutUid: String): Flow<StateUI<Exercise>>

    suspend fun saveImage(uri: Uri): Flow<StateUI<String>>
}