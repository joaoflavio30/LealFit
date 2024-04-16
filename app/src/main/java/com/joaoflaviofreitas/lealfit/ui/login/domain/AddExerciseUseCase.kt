package com.joaoflaviofreitas.lealfit.ui.login.domain

import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import kotlinx.coroutines.flow.Flow

interface AddExerciseUseCase {

    suspend fun execute(exercise: Exercise, workoutUid: String): Flow<StateUI<Exercise>>
}