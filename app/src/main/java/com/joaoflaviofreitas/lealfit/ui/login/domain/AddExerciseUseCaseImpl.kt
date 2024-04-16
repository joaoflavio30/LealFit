package com.joaoflaviofreitas.lealfit.ui.login.domain

import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddExerciseUseCaseImpl @Inject constructor(private val repository: AddExerciseRepository): AddExerciseUseCase {
    override suspend fun execute(exercise: Exercise, workoutUid: String): Flow<StateUI<Exercise>> = repository.createExercise(exercise, workoutUid)
}