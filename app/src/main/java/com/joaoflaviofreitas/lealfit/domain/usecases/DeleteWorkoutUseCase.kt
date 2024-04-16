package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

interface DeleteWorkoutUseCase {

    suspend fun execute(workoutUid: String): Flow<StateUI<String>>
}