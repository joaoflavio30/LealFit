package com.joaoflaviofreitas.lealfit.ui.login.domain

import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Account
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import kotlinx.coroutines.flow.Flow

class RegisterUseCaseImpl(private val registerRepository: RegisterRepository): RegisterUseCase {
    override suspend fun execute(account: Account): Flow<StateUI<Account>> = registerRepository.createAccount(account)
}