package com.joaoflaviofreitas.lealfit.domain.repository

import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

interface AddWorkoutRepository {

    suspend fun createWorkout(workout: Workout): Flow<StateUI<Workout>>
}