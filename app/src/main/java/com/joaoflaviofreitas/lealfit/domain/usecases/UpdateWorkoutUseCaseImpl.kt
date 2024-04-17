package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.domain.repository.WorkoutDetailsRepository
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateWorkoutUseCaseImpl @Inject constructor(private val repository: WorkoutDetailsRepository): UpdateWorkoutUseCase {
    override suspend fun execute(workoutUid: String, newWorkout: Workout): Flow<StateUI<Workout>> = repository.updateWorkout(workoutUid,newWorkout)
}