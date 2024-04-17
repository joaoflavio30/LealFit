package com.joaoflaviofreitas.lealfit.domain.usecases

import com.joaoflaviofreitas.lealfit.domain.model.Account
import com.joaoflaviofreitas.lealfit.domain.repository.RegisterRepository
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

class RegisterUseCaseImpl(private val registerRepository: RegisterRepository): RegisterUseCase {
    override suspend fun execute(account: Account): Flow<StateUI<Account>> = registerRepository.createAccount(account)
}