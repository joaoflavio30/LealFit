package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

interface SignOutUseCase {

    suspend fun execute(): Flow<StateUI<String>>
}