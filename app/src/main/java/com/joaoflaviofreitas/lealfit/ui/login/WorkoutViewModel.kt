package com.joaoflaviofreitas.lealfit.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.lealfit.ui.login.domain.GetWorkoutsUseCase
import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Account
import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Workout
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(private val getWorkoutsUseCase: GetWorkoutsUseCase): ViewModel() {

    private val _uiState = MutableStateFlow<StateUI<Workout>>(StateUI.Idle())
    val uiState = _uiState.asStateFlow()

    fun getWorkouts() {
        viewModelScope.launch(Dispatchers.IO) {
            getWorkoutsUseCase.execute().catch {
                _uiState.value = StateUI.Error(it.message.toString())
            }.collect {
                _uiState.value = it
            }
        }
    }
}