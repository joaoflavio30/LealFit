package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.repository.WorkoutRepository
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignOutUseCaseImpl @Inject constructor(private val repository: WorkoutRepository): SignOutUseCase {
    override suspend fun execute(): Flow<StateUI<String>> = repository.signOut()
}