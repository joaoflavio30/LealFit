package com.joaoflaviofreitas.lealfit.presentation.add_workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.lealfit.domain.usecases.AddWorkoutUseCase
import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.model.StateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddWorkoutViewModel @Inject constructor(private val addWorkoutUseCase: AddWorkoutUseCase): ViewModel() {

    private val _uiState = MutableStateFlow<StateUI<Workout>>(StateUI.Idle())
    val uiState = _uiState.asStateFlow()

    fun createWorkout(workout: Workout) {
        viewModelScope.launch(Dispatchers.IO) {
            addWorkoutUseCase.execute(workout).catch {
                _uiState.value = StateUI.Error(it.message.toString())
            }.collect {
                _uiState.value = it
            }
        }
    }

}