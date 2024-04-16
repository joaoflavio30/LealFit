package com.joaoflaviofreitas.lealfit.ui.login

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.lealfit.ui.login.domain.AddExerciseUseCase
import com.joaoflaviofreitas.lealfit.ui.login.domain.SaveImageUseCase
import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExerciseViewModel @Inject constructor(private val addExerciseUseCase: AddExerciseUseCase,
    private val saveImageUseCase: SaveImageUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<StateUI<Exercise>>(StateUI.Idle())
    val uiState = _uiState.asStateFlow()

    fun createExercise(exercise: Exercise, workoutUid: String, selectedImageUri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            if(selectedImageUri != null) {
                exercise.apply {
                    image =  saveImage(selectedImageUri)
                }
            }
            addExerciseUseCase.execute(exercise, workoutUid).catch {
                _uiState.value = StateUI.Error(it.message.toString())
            }.collect {
                _uiState.value = it
            }
        }
    }

    private suspend fun saveImage(uri: Uri): String? {
        return try {
            when (val state = saveImageUseCase.execute(uri).first()) {
                is StateUI.Processed -> {
                    state.data
                }
                is StateUI.Error -> {
                    null
                }
                is StateUI.Processing -> {
                    null
                }
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }


}