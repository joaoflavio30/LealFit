package com.joaoflaviofreitas.lealfit.data.auth.repository

import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.lealfit.domain.repository.RegisterRepository
import com.joaoflaviofreitas.lealfit.domain.model.Account
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val auth: FirebaseAuth):
    RegisterRepository {
    override suspend fun createAccount(account: Account): Flow<StateUI<Account>>  {
       return flow {
           emit(StateUI.Processing("Wait..."))
           auth.createUserWithEmailAndPassword(account.email, account.password).await()
           emit(StateUI.Processed(account))
           auth.signOut()
       }.catch {  emit(StateUI.Error("Error in Signup"))}
    }
}