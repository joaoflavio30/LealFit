package com.joaoflaviofreitas.lealfit.data.db.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.joaoflaviofreitas.lealfit.data.db.model.WorkoutDTO
import com.joaoflaviofreitas.lealfit.domain.repository.WorkoutRepository
import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val mapWorkoutDTO: (WorkoutDTO) -> Workout,
    private val db: FirebaseFirestore, private val storage: FirebaseStorage,
    private val auth: FirebaseAuth
) : WorkoutRepository {
    override suspend fun getWorkouts(): Flow<StateUI<List<Workout>>> {
        return flow {
            emit(StateUI.Processing("Wait..."))
            val snapshot = db.collection("workout")
                .document(auth.currentUser?.uid ?: throw Exception("User UID is null"))
                .collection("workouts_from_this_user")
                .get()
                .await()

            val workouts = snapshot.map { document ->
                mapWorkoutDTO(document.toObject<WorkoutDTO>().apply {
                    uid = document.id
                })
            }
            emit(StateUI.Processed(workouts))
        }.catch { e ->
            emit(StateUI.Error(e.message.toString()))
        }
    }

}