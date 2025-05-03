package com.example.tutorapp.screen.search

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp.navigation.Screen

data class TutorInfo(
    val name: String,
    val subject: String,
    val university: String,
    val level: String
)

val dummyTutors = listOf(
    TutorInfo("Thanh Mai", "Tiếng Anh", "ĐH Kinh Tế", "Sinh viên"),
    TutorInfo("Anh Tuấn", "Toán", "ĐH Bách Khoa", "Giáo viên"),
    TutorInfo("Hoàng Ngân", "Vật Lý", "ĐH Sư phạm", "Sinh viên")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorSearchScreen(navController: NavController) {
    var selectedSubject by remember { mutableStateOf("") }
    var selectedLevel by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf("") }
    var teachingForm by remember { mutableStateOf("Tại nhà") }
    var searchResults by remember { mutableStateOf(dummyTutors) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tìm gia sư", color = Color.White) },
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
            FilterSection(
                selectedSubject = selectedSubject,
                onSubjectChange = { selectedSubject = it },
                selectedLevel = selectedLevel,
                onLevelChange = { selectedLevel = it },
                selectedLocation = selectedLocation,
                onLocationChange = { selectedLocation = it },
                teachingForm = teachingForm,
                onTeachingFormChange = { teachingForm = it },
                onSearch = {
                    searchResults = dummyTutors.filter {
                        it.subject.contains(selectedSubject, ignoreCase = true)
                    }
                }
            )

            Text("Kết quả:", fontWeight = FontWeight.Bold)

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(searchResults) { tutor ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.TutorDetail.route)
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(tutor.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Môn: ${tutor.subject}")
                            Text("Trường: ${tutor.university}")
                            Text("Trình độ: ${tutor.level}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterSection(
    selectedSubject: String,
    onSubjectChange: (String) -> Unit,
    selectedLevel: String,
    onLevelChange: (String) -> Unit,
    selectedLocation: String,
    onLocationChange: (String) -> Unit,
    teachingForm: String,
    onTeachingFormChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val subjects = listOf("Toán", "Lý", "Hóa", "Anh văn", "Sinh học")
    val levels = listOf("Sinh viên", "Giáo viên")
    val locations = listOf("TP.HCM", "Hà Nội", "Đà Nẵng", "Cần Thơ")

    DropdownField(label = "Môn học", options = subjects, selectedOption = selectedSubject, onOptionSelected = onSubjectChange)
    DropdownField(label = "Trình độ", options = levels, selectedOption = selectedLevel, onOptionSelected = onLevelChange)
    DropdownField(label = "Khu vực", options = locations, selectedOption = selectedLocation, onOptionSelected = onLocationChange)

    Spacer(modifier = Modifier.height(8.dp))
    Text("Hình thức dạy", fontWeight = FontWeight.Bold)
    Row {
        RadioButton(selected = teachingForm == "Tại nhà", onClick = { onTeachingFormChange("Tại nhà") })
        Text("Tại nhà")
        Spacer(modifier = Modifier.width(16.dp))
        RadioButton(selected = teachingForm == "Online", onClick = { onTeachingFormChange("Online") })
        Text("Online")
    }

    Spacer(modifier = Modifier.height(12.dp))
    Button(
        onClick = onSearch,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
    ) {
        Text("TÌM KIẾM", color = Color.White, fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Preview(showSystemUi = true)
@Composable
fun TutorSearchPreview() {
    TutorSearchScreen(navController = rememberNavController())
}
