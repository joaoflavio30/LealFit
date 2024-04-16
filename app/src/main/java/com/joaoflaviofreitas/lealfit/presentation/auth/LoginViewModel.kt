package com.joaoflaviofreitas.lealfit.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.lealfit.domain.usecases.LoginUseCase
import com.joaoflaviofreitas.lealfit.domain.model.Account
import com.joaoflaviofreitas.lealfit.model.StateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {

    private val _uiState = MutableStateFlow<StateUI<Account>>(StateUI.Idle())
    val uiState = _uiState.asStateFlow()

    fun login(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.execute(account).catch {
                _uiState.value = StateUI.Error(it.message.toString())
            }.collect {
                _uiState.value = it
            }
        }
    }

}
