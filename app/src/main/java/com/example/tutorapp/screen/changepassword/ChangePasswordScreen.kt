package com.example.tutorapp.screen.changepassword

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(navController: NavController) {
    var newPass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Đổi mật khẩu", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE91E63))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = newPass,
                onValueChange = { newPass = it },
                label = { Text("Mật khẩu mới") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = confirmPass,
                onValueChange = { confirmPass = it },
                label = { Text("Xác nhận mật khẩu mới") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red)
            }

            if (successMessage.isNotEmpty()) {
                Text(successMessage, color = Color(0xFF4CAF50))
            }

            Button(
                onClick = {
                    errorMessage = ""
                    successMessage = ""

                    when {
                        newPass.isBlank() || confirmPass.isBlank() -> {
                            errorMessage = "Vui lòng nhập đầy đủ thông tin"
                        }
                        newPass.length < 6 -> {
                            errorMessage = "Mật khẩu mới phải từ 6 ký tự trở lên"
                        }
                        newPass != confirmPass -> {
                            errorMessage = "Mật khẩu xác nhận không khớp"
                        }
                        else -> {
                            FirebaseAuth.getInstance().currentUser?.updatePassword(newPass)
                                ?.addOnSuccessListener {
                                    successMessage = "✅ Đổi mật khẩu thành công"
                                }
                                ?.addOnFailureListener {
                                    errorMessage = "Lỗi: ${it.message}"
                                }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .width(225.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63)
                )
            )
             {
                Text("Xác nhận")
            }
        }
    }
}
