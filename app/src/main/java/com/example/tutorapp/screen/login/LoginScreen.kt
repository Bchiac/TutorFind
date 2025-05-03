package com.example.tutorapp.screen.login

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp.navigation.Screen
import androidx.compose.ui.text.style.TextDecoration
import com.example.tutorapp.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun LoginScreen(navController: NavController,
                loginViewModel: LoginViewModel = viewModel()) {// Inject vào) {

    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val errorMessage by loginViewModel.errorMessage.collectAsState()

    val scrollState = rememberScrollState()
    var passwordVisible by remember { mutableStateOf(false) }
    val loginSuccess by loginViewModel.loginSuccess.collectAsState()
    val userRole by loginViewModel.userRole.collectAsState() // <-- thêm biến theo dõi role

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            if (userRole == "ADMIN") {
                navController.navigate("admin_dashboard") {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            } else {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        }
    }
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
            onValueChange = { loginViewModel.onEmailChange(it) },
            label = { Text("Email") },
            isError = email.isBlank() && errorMessage.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
        if (email.isBlank() && errorMessage.isNotEmpty()) {
            Text("Trường này không được để trống",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Ô nhập Mật khẩu ---
        OutlinedTextField(
            value = password,
            onValueChange = { loginViewModel.onPasswordChange(it) },
            label = { Text("Mật khẩu") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Default.Visibility
                else Icons.Default.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
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
            onClick = { loginViewModel.login()
                      },
            modifier = Modifier
                .width(225.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE91E63)
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