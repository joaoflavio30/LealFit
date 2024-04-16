package com.joaoflaviofreitas.lealfit.domain.repository

import com.joaoflaviofreitas.lealfit.domain.model.Account
import com.joaoflaviofreitas.lealfit.model.StateUI
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun loginAccount(account: Account): Flow<StateUI<Account>>
}