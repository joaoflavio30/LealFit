package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

interface AddExerciseUseCase {

    suspend fun execute(exercise: Exercise, workoutUid: String): Flow<StateUI<Exercise>>
}