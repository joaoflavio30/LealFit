package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.domain.repository.WorkoutDetailsRepository
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteExerciseUseCaseImpl @Inject constructor(private val repository: WorkoutDetailsRepository): DeleteExerciseUseCase {
    override suspend fun execute(workout: Workout): Flow<StateUI<Workout>> = repository.deleteExercise(workout)
}