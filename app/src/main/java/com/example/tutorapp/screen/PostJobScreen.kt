package com.example.tutorapp.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PostJobScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("\u0110ăng tin tìm Gia sư", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        TextField(value = "", onValueChange = {}, label = { Text("Môn học") })
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = "", onValueChange = {}, label = { Text("Địa điểm") })
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("\u0110ăng tin")
        }
    }
}