package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.domain.repository.WorkoutRepository
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutsUseCaseImpl @Inject constructor(private val repository: WorkoutRepository):
    GetWorkoutsUseCase {
    override suspend fun execute(): Flow<StateUI<List<Workout>>> = repository.getWorkouts()
}