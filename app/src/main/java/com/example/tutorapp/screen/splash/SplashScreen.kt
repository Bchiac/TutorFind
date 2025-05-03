package com.example.tutorapp.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tutorapp.R
import com.example.tutorapp.datastore.UserPreferences
import com.example.tutorapp.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val email by prefs.userEmail.collectAsState(initial = "")

    LaunchedEffect(email) {
        delay(1500)
        if (email.isNotEmpty()) {
            navController.navigate(Screen.Home.route) {
                popUpTo(0)
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(0)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.tutorfind_picture), // ảnh logo bạn đã thêm
                contentDescription = "TutorFind Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
