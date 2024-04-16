package com.joaoflaviofreitas.lealfit.domain.repository

import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

interface WorkoutDetailsRepository {

    suspend fun deleteWorkout(workoutUid: String): Flow<StateUI<String>>

    suspend fun updateWorkout(workoutUid: String, newWorkout: Workout): Flow<StateUI<Workout>>

    suspend fun deleteExercise(workout: Workout): Flow<StateUI<Workout>>

    suspend fun updateExercise(newWorkout: Workout): Flow<StateUI<Workout>>
}