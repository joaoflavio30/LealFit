package com.joaoflaviofreitas.lealfit.presentation.workout_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.lealfit.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.domain.usecases.DeleteExerciseUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.DeleteWorkoutUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.UpdateExerciseUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.UpdateWorkoutUseCase
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
class WorkoutDetailsViewModel @Inject constructor(
    private val deleteWorkoutUseCase: DeleteWorkoutUseCase,
    private val updateWorkoutUseCase: UpdateWorkoutUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase,
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<StateUI<Workout>>(StateUI.Idle())
    val uiState = _uiState.asStateFlow()

    suspend fun deleteWorkout(workoutUid: String): Flow<StateUI<String>> =
        deleteWorkoutUseCase.execute(workoutUid)

    fun setWorkoutFromArgs(workout: Workout) {
        _uiState.value = StateUI.Processed(workout)
    }

    fun updateWorkout(workoutUid: String, newWorkout: Workout) {
        viewModelScope.launch(Dispatchers.IO) {
            updateWorkoutUseCase.execute(workoutUid, newWorkout).catch {
                _uiState.value = StateUI.Error(it.message.toString())
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun deleteExercise(exerciseToDelete: Exercise, workout: Workout?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_uiState.value is StateUI.Processed) {
                val newWorkout = (_uiState.value as StateUI.Processed<Workout>).data
                newWorkout.exercises?.remove(exerciseToDelete)
                deleteExerciseUseCase.execute(newWorkout).catch {
                    _uiState.value = StateUI.Error(it.message.toString())
                }.collect {
                    _uiState.value = it
                }
            } else {
                if (workout != null) {
                    workout.exercises?.remove(exerciseToDelete)
                    deleteExerciseUseCase.execute(workout).catch {
                        _uiState.value = StateUI.Error(it.message.toString())
                    }.collect {
                        _uiState.value = it
                    }
                }
            }

        }
    }

    fun updateExercise(
        oldExercise: Exercise,
        newExercise: Exercise
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            if (_uiState.value is StateUI.Processed) {
                val workout = (_uiState.value as StateUI.Processed<Workout>).data
                workout.exercises?.remove(oldExercise)
                workout.exercises?.add(newExercise)
                updateExerciseUseCase.execute(workout).catch {
                    _uiState.value = StateUI.Error(it.message.toString())
                }.collect {
                    _uiState.value = it
                }
            }

        }

    }


}