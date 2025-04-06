package com.example.tutorapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp.HomeTopBar
import com.example.tutorapp.navigation.Screen

// Dữ liệu mẫu
val menuItems = listOf(
    MenuItem("Tìm gia sư", Icons.Default.Search),
    MenuItem("Lớp gần đây", Icons.Default.LocationOn),
    MenuItem("Bản đồ lớp", Icons.Default.LocationOn)
)

val dummyClasses = listOf(
    ClassItem("Tiếng Anh", "Thanh Mai", "1,200,000đ"),
    ClassItem("Toán Học", "Nguyễn An", "1,000,000đ"),
    ClassItem("Vật Lý", "Trần Bình", "1,100,000đ")
)

data class MenuItem(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
data class ClassItem(val subject: String, val tutorName: String, val fee: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeTopBar()

        Spacer(modifier = Modifier.height(16.dp))

        // Menu icon
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            menuItems.forEach { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { /* TODO */ }
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Các lớp mới",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
            TextButton(onClick = { /* TODO: Xem tất cả */ }) {
                Text(text = "Xem tất cả", color = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // List class
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            items(dummyClasses) { classItem ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .width(320.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable { navController.navigate(Screen.TutorDetail.route) }
                ) {
                    Column(
                        modifier = Modifier.background(Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFE91E63))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = classItem.subject,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Column(modifier = Modifier.padding(12.dp)) {

                            Text(text = "Gia sư: ${classItem.tutorName}", fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Học phí: ${classItem.fee}",
                                color = Color(0xFF1565C0),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp)) // Thêm khoảng cách

                            // Thêm dòng Xem chi tiết
                            Text(
                                text = "Xem chi tiết >>>",
                                fontSize = 13.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
