package com.example.tutorapp.screen.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _errorMessage.value = "Vui lòng điền đầy đủ thông tin!"
            _loginSuccess.value = false
        } else {
            _errorMessage.value = ""
            _loginSuccess.value = true
        }
    }
}

// fun login() {
//        if (_email.value.isBlank() || _password.value.isBlank()) {
//            _errorMessage.value = "Vui lòng điền đầy đủ thông tin!"
//        } else {
//            _errorMessage.value = ""
//            // TODO: Thực hiện login thật ở đây (ví dụ: gọi API)
//        }
//    }