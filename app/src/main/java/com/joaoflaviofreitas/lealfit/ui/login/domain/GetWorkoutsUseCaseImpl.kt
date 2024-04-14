package com.joaoflaviofreitas.lealfit.ui.login.domain

import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Workout
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import kotlinx.coroutines.flow.Flow

class GetWorkoutsUseCaseImpl: GetWorkoutsUseCase {
    override suspend fun execute(): Flow<StateUI<Workout>> {
        TODO("Not yet implemented")
    }
}