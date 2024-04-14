package com.joaoflaviofreitas.lealfit.ui.login.domain.di

import com.joaoflaviofreitas.lealfit.ui.login.domain.LoginRepository
import com.joaoflaviofreitas.lealfit.ui.login.domain.LoginUseCase
import com.joaoflaviofreitas.lealfit.ui.login.domain.LoginUseCaseImpl
import com.joaoflaviofreitas.lealfit.ui.login.domain.RegisterRepository
import com.joaoflaviofreitas.lealfit.ui.login.domain.RegisterUseCase
import com.joaoflaviofreitas.lealfit.ui.login.domain.RegisterUseCaseImpl
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
    fun providesGetWorkoutsUseCase(workoutRepository: WorkoutRepository): LoginUseCase =
        LoginUseCaseImpl(loginRepository)
}