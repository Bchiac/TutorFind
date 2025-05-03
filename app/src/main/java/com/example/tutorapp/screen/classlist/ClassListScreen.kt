package com.example.tutorapp.screen.classlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import kotlinx.coroutines.tasks.await

data class RegisteredClassItem(
    val subject: String = "",
    val time: String = "",
    val status: String = "Chờ duyệt"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassListScreen(navController: NavController) {
    val classList = remember { mutableStateListOf<RegisteredClassItem>() }
    val db = Firebase.firestore
    val currentUser = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(Unit) {
        if (currentUser != null) {
            val snapshot = db.collection("registrations")
                .whereEqualTo("userId", currentUser.uid)
                .get()
                .await()


            classList.clear()
            snapshot.documents.forEach { doc ->
                val data = doc.data ?: return@forEach
                classList.add(
                    RegisteredClassItem(
                        subject = data["subject"] as? String ?: "",
                        status = data["status"] as? String ?: "Chờ duyệt"
                    )
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Các lớp muốn học", color = Color.White) },
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
                .padding(16.dp)
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(classList) { classItem ->
                    val statusColor = when (classItem.status) {
                        "Đã duyệt" -> Color(0xFF4CAF50)
                        "Từ chối" -> Color.Red
                        "Chờ duyệt" -> Color.Gray
                        else -> Color.Black
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = classItem.subject,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE91E63)
                            )
                            Text(
                                text = "Trạng thái: ${classItem.status}",
                                color = statusColor,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
