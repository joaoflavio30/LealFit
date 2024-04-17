package com.joaoflaviofreitas.lealfit.domain.repository

import android.net.Uri
import com.joaoflaviofreitas.lealfit.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

interface AddExerciseRepository {
    suspend fun createExercise(exercise: Exercise, workoutUid: String): Flow<StateUI<Exercise>>

    suspend fun saveImage(uri: Uri): Flow<StateUI<String>>
}