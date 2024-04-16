package com.joaoflaviofreitas.lealfit.domain.usecases

import android.net.Uri
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

interface SaveImageUseCase {

    suspend fun execute(uri: Uri): Flow<StateUI<String>>
}