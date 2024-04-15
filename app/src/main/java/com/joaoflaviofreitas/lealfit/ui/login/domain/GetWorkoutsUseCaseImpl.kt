package com.joaoflaviofreitas.lealfit.ui.login.domain

import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Workout
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutsUseCaseImpl @Inject constructor(private val repository: WorkoutRepository): GetWorkoutsUseCase {
    override suspend fun execute(): Flow<StateUI<List<Workout>>> = repository.getWorkouts()
}