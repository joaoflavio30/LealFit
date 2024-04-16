package com.joaoflaviofreitas.lealfit.data.db.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.joaoflaviofreitas.lealfit.data.db.model.ExerciseDTO
import com.joaoflaviofreitas.lealfit.data.db.model.WorkoutDTO
import com.joaoflaviofreitas.lealfit.domain.repository.AddExerciseRepository
import com.joaoflaviofreitas.lealfit.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AddExerciseRepositoryImpl @Inject constructor(
    private val mapExercise: (Exercise) -> ExerciseDTO,
    private val db: FirebaseFirestore, private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
) : AddExerciseRepository {
    override suspend fun createExercise(
        exercise: Exercise,
        workoutUid: String
    ): Flow<StateUI<Exercise>> {
        return flow {
            emit(StateUI.Processing("Wait..."))
            val exerciseDTO = mapExercise(exercise)
            val exerciseHashMap = hashMapOf(
                "name" to exerciseDTO.name,
                "observations" to exerciseDTO.observations,
                "image" to exerciseDTO.image
            )
            val document = db.collection("workout")
                .document(auth.currentUser?.uid ?: throw Exception("User UID is null"))
                .collection("workouts_from_this_user")
                .document(workoutUid)
                .get().await()

            val exercises =
                document.toObject<WorkoutDTO>()?.exercises?.toMutableList() ?: mutableListOf()

            exercises.add(exerciseDTO)
            document.reference.update("exercises", exercises).await()

            emit(StateUI.Processed(exercise))
        }.catch { emit(StateUI.Error("Error in Exercise Creation")) }
    }

    override suspend fun saveImage(uri: Uri): Flow<StateUI<String>> {
        return flow {
            emit(StateUI.Processing("Wait..."))
            val uploadTask = storage.reference.child("images/${System.currentTimeMillis()}")
                .putFile(uri).await()
            val url = uploadTask.metadata?.reference?.downloadUrl?.await()
            emit(StateUI.Processed(url.toString()))
        }.catch { emit(StateUI.Error(it.message.toString())) }
    }
}