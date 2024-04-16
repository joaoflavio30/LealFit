package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.domain.repository.AddExerciseRepository
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddExerciseUseCaseImpl @Inject constructor(private val repository: AddExerciseRepository):
    AddExerciseUseCase {
    override suspend fun execute(exercise: Exercise, workoutUid: String): Flow<StateUI<Exercise>> = repository.createExercise(exercise, workoutUid)
}