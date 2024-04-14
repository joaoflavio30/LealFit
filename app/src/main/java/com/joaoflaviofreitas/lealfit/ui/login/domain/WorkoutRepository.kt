package com.joaoflaviofreitas.lealfit.ui.login.domain

import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Workout
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    suspend fun getWorkouts(): Flow<StateUI<Workout>>
}