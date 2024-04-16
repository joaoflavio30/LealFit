package com.joaoflaviofreitas.lealfit.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.joaoflaviofreitas.lealfit.data.db.repository.AddExerciseRepositoryImpl
import com.joaoflaviofreitas.lealfit.data.db.repository.WorkoutRepositoryImpl
import com.joaoflaviofreitas.lealfit.data.mapper.mapExercise
import com.joaoflaviofreitas.lealfit.data.mapper.mapExerciseDTO
import com.joaoflaviofreitas.lealfit.data.mapper.mapWorkout
import com.joaoflaviofreitas.lealfit.data.mapper.mapWorkoutDTO
import com.joaoflaviofreitas.lealfit.data.db.model.ExerciseDTO
import com.joaoflaviofreitas.lealfit.data.db.model.WorkoutDTO
import com.joaoflaviofreitas.lealfit.data.db.repository.AddWorkoutRepositoryImpl
import com.joaoflaviofreitas.lealfit.data.auth.repository.LoginRepositoryImpl
import com.joaoflaviofreitas.lealfit.data.auth.repository.RegisterRepositoryImpl
import com.joaoflaviofreitas.lealfit.domain.repository.AddExerciseRepository
import com.joaoflaviofreitas.lealfit.domain.repository.AddWorkoutRepository
import com.joaoflaviofreitas.lealfit.domain.repository.LoginRepository
import com.joaoflaviofreitas.lealfit.domain.repository.RegisterRepository
import com.joaoflaviofreitas.lealfit.domain.repository.WorkoutRepository
import com.joaoflaviofreitas.lealfit.domain.model.Exercise
import com.joaoflaviofreitas.lealfit.domain.model.Workout
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private fun makeWorkoutDTOMapper(): (WorkoutDTO) -> Workout = { workoutDTO ->
        mapWorkoutDTO(workoutDTO)
    }

    private fun makeExerciseDTOMapper(): (ExerciseDTO) -> Exercise = { exerciseDTO ->
        mapExerciseDTO(exerciseDTO)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFireStoreInstance(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun providesRegisterRepository(auth: FirebaseAuth): RegisterRepository =
        RegisterRepositoryImpl(auth)

    @Provides
    @Singleton
    fun providesLoginRepository(auth: FirebaseAuth): LoginRepository =
        LoginRepositoryImpl(auth)

    @Provides
    @Singleton
    fun providesWorkoutRepository(
        db: FirebaseFirestore,
        storage: FirebaseStorage,
        auth: FirebaseAuth
    ): WorkoutRepository =
        WorkoutRepositoryImpl(makeWorkoutDTOMapper(), db, storage, auth)

    private fun makeWorkoutMapper(): (Workout) -> WorkoutDTO = { workout ->
        mapWorkout(workout)
    }

    @Provides
    @Singleton
    fun providesAddWorkoutRepository(
        db: FirebaseFirestore,
        auth: FirebaseAuth
    ): AddWorkoutRepository =
        AddWorkoutRepositoryImpl(makeWorkoutMapper(), db, auth)

    private fun makeExerciseMapper(): (Exercise) -> ExerciseDTO = { exercise ->
        mapExercise(exercise)
    }

    @Provides
    @Singleton
    fun providesAddExerciseRepository(
        db: FirebaseFirestore,
        auth: FirebaseAuth,
        storage: FirebaseStorage
    ): AddExerciseRepository =
        AddExerciseRepositoryImpl(makeExerciseMapper(), db, auth, storage)
}