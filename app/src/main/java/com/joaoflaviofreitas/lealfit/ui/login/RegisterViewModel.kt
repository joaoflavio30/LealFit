package com.joaoflaviofreitas.lealfit.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.lealfit.ui.login.domain.RegisterUseCase
import com.joaoflaviofreitas.lealfit.ui.login.domain.model.Account
import com.joaoflaviofreitas.lealfit.ui.login.model.StateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase): ViewModel() {

 private val _uiState = MutableStateFlow<StateUI<Account>>(StateUI.Idle())
    val uiState = _uiState.asStateFlow()

    fun signup(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            registerUseCase.execute(account).catch {
                _uiState.value = StateUI.Error(it.message.toString())
            }.collect {
                _uiState.value = it
            }
        }
    }
}