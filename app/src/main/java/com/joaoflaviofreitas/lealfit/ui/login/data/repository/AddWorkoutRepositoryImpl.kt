package com.joaoflaviofreitas.lealfit.ui.login.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.joaoflaviofreitas.lealfit.ui.login.data.model.WorkoutDTO
import com.joaoflaviofreitas.lealfit.ui.login.domain.AddWorkoutRepository
import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Workout
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AddWorkoutRepositoryImpl @Inject constructor(
    private val mapWorkout: (Workout) -> WorkoutDTO,
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : AddWorkoutRepository {
    override suspend fun createWorkout(workout: Workout): Flow<StateUI<Workout>> {
        return flow {
            emit(StateUI.Processing("Wait..."))
            val workoutDTO = mapWorkout(workout)
            val workoutHashMap = hashMapOf(
                "name" to workoutDTO.name,
                "date" to workoutDTO.date,
                "description" to workoutDTO.description,
                "exercises" to workoutDTO.exercises
            )
            db.collection("workout")
                .document(auth.currentUser?.uid ?: throw Exception("User UID is null"))
                .collection("workouts_from_this_user")
                .add(workoutHashMap)
                .await()
            emit(StateUI.Processed(workout))
        }.catch { emit(StateUI.Error("Error in Workout Creation")) }


    }
}