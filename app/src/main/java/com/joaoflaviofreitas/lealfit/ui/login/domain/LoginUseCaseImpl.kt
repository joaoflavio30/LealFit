package com.joaoflaviofreitas.lealfit.ui.login.domain

import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Account
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import kotlinx.coroutines.flow.Flow

class LoginUseCaseImpl(private val repository: LoginRepository): LoginUseCase {
    override suspend fun execute(account: Account): Flow<StateUI<Account>> = repository.loginAccount(account)
}