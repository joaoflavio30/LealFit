package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.domain.repository.WorkoutDetailsRepository
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateExerciseUseCaseImpl @Inject constructor(private val repository: WorkoutDetailsRepository): UpdateExerciseUseCase {
    override suspend fun execute(
        newWorkout: Workout
    ): Flow<StateUI<Workout>> = repository.updateExercise(newWorkout)
}