package com.example.tutorapp.screen.tutordetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorDetailScreen(
    navController: NavController,
    viewModel: TutorDetailViewModel = viewModel()
) {
    val tutor by viewModel.tutor.collectAsState()

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
                .padding(innerPadding) // lấy padding chuẩn
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nội dung avatar + thông tin gia sư
            Image(
                painter = painterResource(id = R.drawable.tutor),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(tutor.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Trình độ: ${tutor.level}", style = MaterialTheme.typography.bodyMedium)
            Text("Trường: ${tutor.university}", style = MaterialTheme.typography.bodyMedium)
            Text("Chuyên ngành: ${tutor.major}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.LightGray)
            ) {
                Text(
                    text = "Ảnh bằng cấp",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))



            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* TODO: Liên hệ */ },
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

@Preview(showSystemUi = true)
@Composable
fun TutorPreview() {
    TutorDetailScreen(navController = rememberNavController())
}
