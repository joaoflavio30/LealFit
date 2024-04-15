package com.joaoflaviofreitas.lealfit.ui.login.data.db

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.joaoflaviofreitas.lealfit.ui.login.data.model.WorkoutDTO
import com.joaoflaviofreitas.lealfit.ui.login.domain.WorkoutRepository
import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Workout
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(private val mapWorkoutDTO: (WorkoutDTO) -> Workout,
    private val db: FirebaseFirestore, private val storage: FirebaseStorage,
    private val auth: FirebaseAuth): WorkoutRepository {
    override suspend fun getWorkouts(): Flow<StateUI<List<Workout>>> {
        return flow {
            emit(StateUI.Processing("Wait..."))
                val snapshot = db.collection("workout")
                    .document(auth.currentUser?.uid ?: throw Exception("User UID is null"))
                    .collection("workouts_from_this_user")
                    .get()
                    .await()

                val workouts = snapshot.map { document ->
                    mapWorkoutDTO(document.toObject<WorkoutDTO>())
                }
                Log.d("teste Repository", "${snapshot}")
                emit(StateUI.Processed(workouts))
        }.catch { e ->
            emit(StateUI.Error(e.message.toString()))
        }
    }

}