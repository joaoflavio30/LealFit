package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

interface AddWorkoutUseCase {
    suspend fun execute(workout: Workout): Flow<StateUI<Workout>>
}