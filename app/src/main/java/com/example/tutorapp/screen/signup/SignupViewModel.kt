package com.example.tutorapp.screen.signup


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignupViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _emailError = MutableStateFlow("")
    val emailError: StateFlow<String> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow("")
    val passwordError: StateFlow<String> = _passwordError.asStateFlow()

    private val _confirmPasswordError = MutableStateFlow("")
    val confirmPasswordError: StateFlow<String> = _confirmPasswordError.asStateFlow()

    private val _signupSuccess = MutableStateFlow(false)
    val signupSuccess: StateFlow<Boolean> = _signupSuccess.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _emailError.value = ""
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _passwordError.value = ""
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
        _confirmPasswordError.value = ""
    }

    fun signup() {
        var isValid = true

        if (_email.value.isBlank()) {
            _emailError.value = "Email không được để trống"
            isValid = false
        }

        if (_password.value.length < 6) {
            _passwordError.value = "Mật khẩu quá ngắn"
            isValid = false
        }

        if (_confirmPassword.value != _password.value) {
            _confirmPasswordError.value = "Mật khẩu xác nhận không khớp"
            isValid = false
        }

        _signupSuccess.value = isValid
    }
}
