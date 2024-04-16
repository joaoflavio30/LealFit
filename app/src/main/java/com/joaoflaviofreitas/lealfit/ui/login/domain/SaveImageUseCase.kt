package com.joaoflaviofreitas.lealfit.ui.login.domain

import android.net.Uri
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import kotlinx.coroutines.flow.Flow

interface SaveImageUseCase {

    suspend fun execute(uri: Uri): Flow<StateUI<String>>
}