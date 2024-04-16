package com.joaoflaviofreitas.lealfit.presentation.add_exercicie

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.lealfit.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.domain.usecases.AddExerciseUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.SaveImageUseCase
import com.joaoflaviofreitas.lealfit.model.StateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
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
                    saveImageUseCase.execute(selectedImageUri).collectLatest {
                        if(it is StateUI.Processed) image = it.data
                    }
                }
            }
            addExerciseUseCase.execute(exercise, workoutUid).catch {
                _uiState.value = StateUI.Error(it.message.toString())
            }.collect {
                _uiState.value = it
            }
        }
    }


}