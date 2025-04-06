package com.example.tutorapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp.navigation.Screen
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration

import com.example.tutorapp.R

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)

    ) {
        // --- Phần trên: Nền màu hồng + Logo TutorFind ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(Color(0xFFE91E63)), // Màu hồng
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.tutor),
                contentDescription = "Logo TutorFind",
                modifier = Modifier
                    .size(240.dp) // Tùy chỉnh to/nhỏ logo
            )
        }

    Spacer(modifier = Modifier.height(32.dp))

        // --- Ô nhập Email ---
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            isError = email.isBlank() && errorMessage.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp) // Cách lề 32dp mỗi bên
        )
        if (email.isBlank() && errorMessage.isNotEmpty()) {
            Text("Trường này không được để trống",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp), // Cách lề 32dp mỗi bên,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Ô nhập Mật khẩu ---
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            isError = password.isBlank() && errorMessage.isNotEmpty(),
            modifier = Modifier.
            fillMaxWidth()
                .padding(horizontal = 32.dp), // Cách lề 32dp mỗi bên
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

        )
        if (password.isBlank() && errorMessage.isNotEmpty()) {
            Text("Trường này không được để trống",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Nút Đăng nhập ---
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Vui lòng điền đầy đủ thông tin"
                } else {
                    navController.navigate(Screen.Home.route)
                }
            },
            modifier = Modifier
                .width(225.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE91E63) // Đây là màu cam, bạn đổi mã màu tùy ý
            )
        ) {
            Text("Đăng nhập")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Link chuyển màn Đăng ký ---
        Row(
            modifier = Modifier.fillMaxWidth(), // Bắt Row rộng full màn hình
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center // Canh giữa nội dung trong Row
        ) {
            Text(
                text = "Bạn chưa có tài khoản?",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(0.dp))
            TextButton(onClick = { navController.navigate(Screen.Signup.route) }) {
                Text(
                    text = "Đăng ký ngay",
                    color = Color(0xFFE91E63),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }

    }
}


@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}