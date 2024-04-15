package com.joaoflaviofreitas.lealfit.ui.login.domain

import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Workout
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddWorkoutUseCaseImpl @Inject constructor(private val repository: AddWorkoutRepository): AddWorkoutUseCase {
    override suspend fun execute(workout: Workout): Flow<StateUI<Workout>> = repository.createWorkout(workout)
}