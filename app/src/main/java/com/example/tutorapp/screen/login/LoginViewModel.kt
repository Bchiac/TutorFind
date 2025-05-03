package com.example.tutorapp.screen.login

import androidx.lifecycle.ViewModel
import com.example.tutorapp.firebase.FirebaseAuthManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    private val _userRole = MutableStateFlow("USER") // ✅ role mặc định là USER
    val userRole: StateFlow<String> = _userRole.asStateFlow()

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
            FirebaseAuthManager.login(
                email = _email.value,
                password = _password.value,
                onSuccess = {
                    _errorMessage.value = ""

                    // 🔍 Lấy role từ Firestore sau khi login thành công
                    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                    val db = Firebase.firestore

                    db.collection("users").document(uid).get()
                        .addOnSuccessListener { doc ->
                            val role = doc.getString("role") ?: "USER"
                            _userRole.value = role
                            _loginSuccess.value = true
                        }
                        .addOnFailureListener {
                            _errorMessage.value = "Không lấy được thông tin người dùng"
                            _loginSuccess.value = false
                        }
                },
                onError = {
                    _errorMessage.value = it
                    _loginSuccess.value = false
                }
            )
        }
    }
}
