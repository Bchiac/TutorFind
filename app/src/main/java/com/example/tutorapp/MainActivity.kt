package com.example.tutorapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp.navigation.NavGraph
import com.example.tutorapp.ui.theme.TutorAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorAppTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController) // 👈 TRUYỀN navController vào đây!!!
            }
        }
    }
}
