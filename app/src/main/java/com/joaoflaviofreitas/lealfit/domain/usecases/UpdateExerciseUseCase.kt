package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

interface UpdateExerciseUseCase {

    suspend fun execute(newWorkout: Workout): Flow<StateUI<Workout>>
}