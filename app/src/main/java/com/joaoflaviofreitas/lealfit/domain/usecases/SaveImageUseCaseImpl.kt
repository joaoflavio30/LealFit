package com.joaoflaviofreitas.lealfit.domain.usecases

import android.net.Uri
import com.joaoflaviofreitas.lealfit.domain.repository.AddExerciseRepository
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveImageUseCaseImpl @Inject constructor(private val repository: AddExerciseRepository):
    SaveImageUseCase {
    override suspend fun execute(uri: Uri): Flow<StateUI<String>> = repository.saveImage(uri)
}