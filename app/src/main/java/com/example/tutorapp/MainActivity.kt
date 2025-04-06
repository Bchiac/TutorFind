package com.example.tutorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tutorapp.ui.theme.TutorAppTheme
import com.example.tutorapp.navigation.NavGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorAppTheme {
                NavGraph()
            }
        }
    }
}