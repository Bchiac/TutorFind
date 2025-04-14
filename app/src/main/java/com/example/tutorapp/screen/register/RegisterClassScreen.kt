package com.example.tutorapp.screen.register

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterClassScreen(
    navController: NavController,
    viewModel: RegisterClassViewModel = viewModel()
) {
    val classDetail by viewModel.classDetail.collectAsState()
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Đăng ký tham gia lớp học",
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
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),

        ){


        Spacer(modifier = Modifier.height(12.dp))

        // Khung thông tin giáo viên (clickable để xem chi tiết)
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Screen.TutorDetail.route) },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .border(1.dp, Color.Gray, CircleShape)
                ) {
                    // Ảnh đại diện placeholder
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Họ tên: ${classDetail.tutorName}", color = Color.Black, fontWeight = FontWeight.Bold)
                    Text("Trình độ: ${classDetail.degree}")
                    Text("Trường: ${classDetail.university}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Thông tin lớp học
        Text("Mã lớp: 1508", color = Color(0xFFE91E63))
        Text("Lớp học: ${classDetail.subject}", fontWeight = FontWeight.Bold)
        Text("Số lượng học sinh: 1")

        Spacer(modifier = Modifier.height(8.dp))

        Text("Ngày học trong tuần:", color = Color(0xFFE91E63))
        Row {
            (2..8).forEach { day ->
                val selected = classDetail.schedule.contains(day)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(4.dp)
                        .border(1.dp, Color.Red, CircleShape)
                        .background(if (selected) Color(0xFFE91E63) else Color.White, CircleShape)
                ) {
                    Text(
                        text = if (day == 8) "CN" else day.toString(),
                        color = if (selected) Color.White else Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Thời gian: ${classDetail.time}", color = Color(0xFFE91E63))
        Text("Từ ${classDetail.startDate} ➔ ${classDetail.endDate}")

        Spacer(modifier = Modifier.height(8.dp))

        Text("Địa điểm học:", color = Color(0xFFE91E63))
        Text(classDetail.address)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Hình thức:", color = Color(0xFFE91E63))
        Text(classDetail.teachingForm)

        Spacer(modifier = Modifier.height(12.dp))

        Text("HỌC PHÍ / THÁNG", color = Color.Red, fontWeight = FontWeight.Bold)
        Text(
            text = classDetail.fee,
            fontSize = 24.sp,
            color = Color(0xFF1565C0),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Button đăng ký tham gia
        Button(
            onClick = { /* TODO: Xử lý đăng ký */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE91E63),
                contentColor = Color.White
            )
        ) {
            Text(text = "ĐĂNG KÝ THAM GIA", fontWeight = FontWeight.Bold)
        }
    }
        }
}

@Preview(showSystemUi = true)
@Composable
fun RegisterPreview() {
    RegisterClassScreen(navController = rememberNavController())
}
