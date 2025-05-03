package com.example.tutorapp.screen.admin

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class RegistrationItem(
    val id: String = "",
    val userName: String = "",
    val subject: String = "",
    val tutorId: String = "",
    var status: String = "Chờ duyệt"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(navController: NavController) {
    val db = Firebase.firestore
    val registrations = remember { mutableStateListOf<RegistrationItem>() }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Load dữ liệu khi vào màn
    LaunchedEffect(Unit) {
        try {
            val snapshot = db.collection("registrations").get().await()
            registrations.clear()
            snapshot.documents.forEach { doc ->
                val data = doc.data ?: return@forEach
                registrations.add(
                    RegistrationItem(
                        id = doc.id,
                        userName = data["userName"] as? String ?: "",
                        subject = data["subject"] as? String ?: "",
                        tutorId = data["tutorId"] as? String ?: "",
                        status = data["status"] as? String ?: "Chờ duyệt"
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("AdminDashboard", "Lỗi lấy dữ liệu: $e")
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(280.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Xin chào Admin!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "Đăng xuất",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                FirebaseAuth.getInstance().signOut()
                                navController.navigate("login") {
                                    popUpTo("admin_dashboard") { inclusive = true }
                                }
                                scope.launch { drawerState.close() }
                            }
                            .padding(12.dp),
                        fontSize = 16.sp
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Quản lý lớp học", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE91E63))
                )
            }
        ) { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(registrations) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            // Hàng chứa tên lớp + xoá
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text("📘 Lớp: ${item.subject}")
                                IconButton(onClick = {
                                    db.collection("registrations").document(item.id)
                                        .delete()
                                        .addOnSuccessListener {
                                            registrations.remove(item)
                                            Log.d("AdminDashboard", "Đã xoá: ${item.id}")
                                        }
                                }) {
                                    Icon(Icons.Default.Close, contentDescription = "Xoá", tint = Color.Red)
                                }
                            }

                            Text("👤 ${item.userName}")
                            Spacer(modifier = Modifier.height(4.dp))

                            var selectedStatus by remember { mutableStateOf(item.status) }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("📌 Trạng thái: ")
                                Spacer(modifier = Modifier.width(8.dp))
                                StatusDropdownMenu(
                                    currentStatus = selectedStatus,
                                    onStatusSelected = { newStatus ->
                                        selectedStatus = newStatus
                                        item.status = newStatus
                                        db.collection("registrations").document(item.id)
                                            .update("status", newStatus)
                                            .addOnSuccessListener {
                                                Log.d("AdminDashboard", "Cập nhật trạng thái: $newStatus")
                                            }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusDropdownMenu(
    currentStatus: String,
    onStatusSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Chờ duyệt", "Đã duyệt", "Từ chối")

    // Chọn màu theo trạng thái
    val statusColor = when (currentStatus) {
        "Đã duyệt" -> Color.Green
        "Từ chối" -> Color.Red
        else -> Color.Gray
    }

    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(containerColor = statusColor)
        ) {
            Text(currentStatus, color = Color.White)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onStatusSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

