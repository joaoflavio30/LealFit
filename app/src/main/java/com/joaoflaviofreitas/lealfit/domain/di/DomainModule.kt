package com.joaoflaviofreitas.lealfit.domain.di

import com.joaoflaviofreitas.lealfit.domain.repository.AddExerciseRepository
import com.joaoflaviofreitas.lealfit.domain.usecases.AddExerciseUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.AddExerciseUseCaseImpl
import com.joaoflaviofreitas.lealfit.domain.repository.AddWorkoutRepository
import com.joaoflaviofreitas.lealfit.domain.usecases.AddWorkoutUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.AddWorkoutUseCaseImpl
import com.joaoflaviofreitas.lealfit.domain.usecases.GetWorkoutsUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.GetWorkoutsUseCaseImpl
import com.joaoflaviofreitas.lealfit.domain.repository.LoginRepository
import com.joaoflaviofreitas.lealfit.domain.usecases.LoginUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.LoginUseCaseImpl
import com.joaoflaviofreitas.lealfit.domain.repository.RegisterRepository
import com.joaoflaviofreitas.lealfit.domain.repository.WorkoutDetailsRepository
import com.joaoflaviofreitas.lealfit.domain.usecases.RegisterUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.RegisterUseCaseImpl
import com.joaoflaviofreitas.lealfit.domain.usecases.SaveImageUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.SaveImageUseCaseImpl
import com.joaoflaviofreitas.lealfit.domain.repository.WorkoutRepository
import com.joaoflaviofreitas.lealfit.domain.usecases.DeleteExerciseUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.DeleteExerciseUseCaseImpl
import com.joaoflaviofreitas.lealfit.domain.usecases.DeleteWorkoutUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.DeleteWorkoutUseCaseImpl
import com.joaoflaviofreitas.lealfit.domain.usecases.SignOutUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.SignOutUseCaseImpl
import com.joaoflaviofreitas.lealfit.domain.usecases.UpdateExerciseUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.UpdateExerciseUseCaseImpl
import com.joaoflaviofreitas.lealfit.domain.usecases.UpdateWorkoutUseCase
import com.joaoflaviofreitas.lealfit.domain.usecases.UpdateWorkoutUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun providesRegisterUseCase(registerRepository: RegisterRepository): RegisterUseCase =
        RegisterUseCaseImpl(registerRepository)

    @Provides
    @Singleton
    fun providesLoginUseCase(loginRepository: LoginRepository): LoginUseCase =
        LoginUseCaseImpl(loginRepository)

    @Provides
    @Singleton
    fun providesGetWorkoutsUseCase(workoutRepository: WorkoutRepository): GetWorkoutsUseCase =
        GetWorkoutsUseCaseImpl(workoutRepository)

    @Provides
    @Singleton
    fun providesAddWorkoutUseCase(addWorkoutRepository: AddWorkoutRepository): AddWorkoutUseCase =
        AddWorkoutUseCaseImpl(addWorkoutRepository)

    @Provides
    @Singleton
    fun providesAddExerciseUseCase(addExerciseRepository: AddExerciseRepository): AddExerciseUseCase =

        AddExerciseUseCaseImpl(addExerciseRepository)

    @Provides
    @Singleton
    fun providesSaveImageUseCase(addExerciseRepository: AddExerciseRepository): SaveImageUseCase =
        SaveImageUseCaseImpl(addExerciseRepository)

    @Provides
    @Singleton
    fun providesSignOutUseCase(workoutRepository: WorkoutRepository): SignOutUseCase =
        SignOutUseCaseImpl(workoutRepository)

    @Provides
    @Singleton
    fun providesDeleteWorkoutUseCase(workoutDetailsRepository: WorkoutDetailsRepository): DeleteWorkoutUseCase =
        DeleteWorkoutUseCaseImpl(workoutDetailsRepository)

    @Provides
    @Singleton
    fun providesDeleteExerciseUseCase(workoutDetailsRepository: WorkoutDetailsRepository): DeleteExerciseUseCase =
        DeleteExerciseUseCaseImpl(workoutDetailsRepository)

    @Provides
    @Singleton
    fun providesUpdateWorkoutUseCase(workoutDetailsRepository: WorkoutDetailsRepository): UpdateWorkoutUseCase =
        UpdateWorkoutUseCaseImpl(workoutDetailsRepository)

    @Provides
    @Singleton
    fun providesUpdateExerciseUseCase(workoutDetailsRepository: WorkoutDetailsRepository): UpdateExerciseUseCase =
        UpdateExerciseUseCaseImpl(workoutDetailsRepository)
}