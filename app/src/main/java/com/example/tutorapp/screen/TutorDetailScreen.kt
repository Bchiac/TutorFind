package com.example.tutorapp.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tutorapp.navigation.Screen

@Composable
fun TutorDetailScreen(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Chi tiết Gia sư", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Tên: Nguyễn Văn A")
        Text("Chuyên môn: Toán cấp 2")
        Text("Kinh nghiệm: 3 năm")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate(Screen.PostJob.route) }) {
            Text("Liên hệ Gia sư")
        }
    }
}