package com.joaoflaviofreitas.lealfit.ui.login.data.di

import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.lealfit.ui.login.data.repository.LoginRepositoryImpl
import com.joaoflaviofreitas.lealfit.ui.login.data.repository.RegisterRepositoryImpl
import com.joaoflaviofreitas.lealfit.ui.login.domain.LoginRepository
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
object DataModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRegisterRepository(auth: FirebaseAuth): RegisterRepository =
        RegisterRepositoryImpl(auth)

    @Provides
    @Singleton
    fun providesLoginRepository(auth: FirebaseAuth): LoginRepository =
        LoginRepositoryImpl(auth)
}