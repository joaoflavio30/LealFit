package com.joaoflaviofreitas.lealfit.domain.repository

import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    suspend fun getWorkouts(): Flow<StateUI<List<Workout>>>

    suspend fun signOut(): Flow<StateUI<String>>
}