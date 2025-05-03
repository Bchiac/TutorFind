package com.example.tutorapp.screen.tutordetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorDetailScreen(
    navController: NavController,
    tutorId: String, // ✅ Thêm tham số
    viewModel: TutorDetailViewModel = viewModel()
) {
    val tutor by viewModel.tutor.collectAsState()
    val context = LocalContext.current

    // ✅ Gọi dữ liệu từ Firestore
    LaunchedEffect(tutorId) {
        viewModel.loadTutorById(tutorId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Thông tin giáo viên",
                        color = Color.White,
                        style = TextStyle(fontSize = 18.sp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE91E63)
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ảnh đại diện giáo viên
            AsyncImage(
                model = tutor.avatarUrl,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(tutor.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Trình độ: ${tutor.level}", style = MaterialTheme.typography.bodyMedium)
            Text("Lớp dạy: ${tutor.subject}", style = MaterialTheme.typography.bodyMedium)
            Text("Trường: ${tutor.university}", style = MaterialTheme.typography.bodyMedium)
            Text("Chuyên ngành: ${tutor.major}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(24.dp))

            val context = LocalContext.current

            Button(
                onClick = {
                    val phone = tutor.phoneNumber.trim()
                    if (phone.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$phone")
                        }
                        context.startActivity(intent)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63)
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Liên hệ ngay", color = Color.White)
            }

        }
    }
}

