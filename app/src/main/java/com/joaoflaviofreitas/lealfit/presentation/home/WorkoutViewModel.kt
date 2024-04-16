package com.joaoflaviofreitas.lealfit.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.lealfit.domain.usecases.GetWorkoutsUseCase
import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.domain.usecases.SignOutUseCase
import com.joaoflaviofreitas.lealfit.model.StateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(private val getWorkoutsUseCase: GetWorkoutsUseCase,
    private val signOutUseCase: SignOutUseCase): ViewModel() {

    private val _uiState = MutableStateFlow<StateUI<List<Workout>>>(StateUI.Idle())
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

    suspend fun signOut(): Flow<StateUI<String>> = signOutUseCase.execute()
}