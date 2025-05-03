package com.example.tutorapp.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tutorapp.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.Normalizer
import java.util.regex.Pattern

fun String.removeAccents(): String {
    val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(normalized).replaceAll("")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(

    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val classList by homeViewModel.classList.collectAsState()
    val searchQuery by homeViewModel.searchQuery.collectAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val filteredList = classList.filter {
        it.subject.removeAccents().contains(searchQuery.removeAccents(), ignoreCase = true)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController = navController, drawerState = drawerState)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TutorApp", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE91E63))
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { homeViewModel.onSearchQueryChanged(it) },
                    label = { Text("Tìm môn học...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Các lớp mới",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredList) { classItem ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("register_class/${classItem.tutorId}")
                                }
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = classItem.subject,
                                    color = Color(0xFFE91E63),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Gia sư: ${classItem.tutorName}")
                                Text(
                                    text = "Học phí: ${classItem.fee}",
                                    color = Color(0xFF1565C0),
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Xem chi tiết >>>", fontSize = 13.sp, color = Color.Gray)
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet(
        modifier = Modifier.width(280.dp) // 👈 giới hạn chiều rộng menu
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Xin chào, User!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))

            DrawerItem("Trang chủ") {
                scope.launch { drawerState.close() }
                navController.navigate(Screen.Home.route)
            }
            DrawerItem("Đổi mật khẩu") {
                scope.launch { drawerState.close() }
                navController.navigate(Screen.ChangePassword.route)
            }
            DrawerItem("Các lớp muốn học") {
                scope.launch { drawerState.close() }
                navController.navigate(Screen.ClassList.route)
            }
            DrawerItem("Đăng xuất") {
                FirebaseAuth.getInstance().signOut()
                scope.launch {
                    drawerState.close()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            }
        }
    }
}


@Composable
fun DrawerItem(title: String, onClick: () -> Unit) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        fontSize = 16.sp
    )
}
