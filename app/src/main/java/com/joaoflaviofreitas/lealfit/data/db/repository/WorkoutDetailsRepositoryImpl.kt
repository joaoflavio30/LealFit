package com.joaoflaviofreitas.lealfit.data.db.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.joaoflaviofreitas.lealfit.data.db.model.WorkoutDTO
import com.joaoflaviofreitas.lealfit.domain.model.Workout
import com.joaoflaviofreitas.lealfit.domain.repository.WorkoutDetailsRepository
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkoutDetailsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val mapWorkout: (Workout) -> WorkoutDTO,
    private val mapWorkoutDto: (WorkoutDTO) -> Workout,

    ) : WorkoutDetailsRepository {
    override suspend fun deleteWorkout(workoutUid: String): Flow<StateUI<String>> {
        return flow {
            emit(StateUI.Processing("Wait..."))
            db.collection("workout")
                .document(auth.currentUser?.uid ?: throw Exception("User UID is null"))
                .collection("workouts_from_this_user")
                .document(workoutUid)
                .delete()
                .await()
            emit(StateUI.Processed("Success delete"))
        }.catch { emit(StateUI.Error(it.message.toString())) }
    }

    override suspend fun updateWorkout(
        workoutUid: String,
        newWorkout: Workout
    ): Flow<StateUI<Workout>> {
        return flow {
            emit(StateUI.Processing("Wait..."))
            val newWorkoutDto = mapWorkout(newWorkout)
            db.collection("workout")
                .document(auth.currentUser?.uid ?: throw Exception("User UID is null"))
                .collection("workouts_from_this_user")
                .document(workoutUid)
                .set(newWorkoutDto)
                .await()
            emit(StateUI.Processed(newWorkout))
        }.catch { emit(StateUI.Error(it.message.toString())) }
    }

    override suspend fun deleteExercise(
        workout: Workout
    ): Flow<StateUI<Workout>> {
        return flow {
            emit(StateUI.Processing("Wait..."))
            val workoutDto = mapWorkout(workout)
            db.collection("workout")
                .document(auth.currentUser?.uid ?: throw Exception("User UID is null"))
                .collection("workouts_from_this_user")
                .document(workout.uid?:"")
                .set(workoutDto).await()



            val updatedWorkout =  db.collection("workout")
                .document(auth.currentUser?.uid ?: throw Exception("User UID is null"))
                .collection("workouts_from_this_user")
                .document(workout.uid?:"").get().await()

            emit(StateUI.Processed(mapWorkoutDto(updatedWorkout.toObject<WorkoutDTO>()?: WorkoutDTO())))
        }.catch {
            emit(StateUI.Error(it.message.toString()))
        }
    }


    override suspend fun updateExercise(
        newWorkout: Workout
    ): Flow<StateUI<Workout>> {
        return flow {
            emit(StateUI.Processing("Wait..."))
            val workoutDto = mapWorkout(newWorkout)
            db.collection("workout")
                .document(auth.currentUser?.uid ?: throw Exception("User UID is null"))
                .collection("workouts_from_this_user")
                .document(newWorkout.uid?:"")
                .set(workoutDto).await()


            val updatedWorkout =  db.collection("workout")
                .document(auth.currentUser?.uid ?: throw Exception("User UID is null"))
                .collection("workouts_from_this_user")
                .document(newWorkout.uid?:"").get().await()

            emit(StateUI.Processed(mapWorkoutDto(updatedWorkout.toObject<WorkoutDTO>()?: WorkoutDTO())))
        }.catch { emit(StateUI.Error(it.message.toString())) }
    }
}