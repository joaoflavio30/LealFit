package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.domain.repository.AddWorkoutRepository
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddWorkoutUseCaseImpl @Inject constructor(private val repository: AddWorkoutRepository):
    AddWorkoutUseCase {
    override suspend fun execute(workout: Workout): Flow<StateUI<Workout>> = repository.createWorkout(workout)
}