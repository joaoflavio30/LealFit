package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.model.Account
import com.joaoflaviofreitas.lealfit.domain.repository.LoginRepository
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

class LoginUseCaseImpl(private val repository: LoginRepository): LoginUseCase {
    override suspend fun execute(account: Account): Flow<StateUI<Account>> = repository.loginAccount(account)
}