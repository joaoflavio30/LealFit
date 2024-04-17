package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.repository.WorkoutDetailsRepository
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteWorkoutUseCaseImpl @Inject constructor(private val repository: WorkoutDetailsRepository): DeleteWorkoutUseCase {
    override suspend fun execute(workoutUid: String): Flow<StateUI<String>> = repository.deleteWorkout(workoutUid)
}