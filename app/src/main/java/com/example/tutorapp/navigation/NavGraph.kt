package com.example.tutorapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tutorapp.screen.HomeScreen
import com.example.tutorapp.screen.LoginScreen
import com.example.tutorapp.screen.PostJobScreen
import com.example.tutorapp.screen.SignupScreen
import com.example.tutorapp.screen.TutorDetailScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object TutorDetail : Screen("tutor_detail")
    object PostJob : Screen("post_job")
}

@Composable
fun NavGraph(startDestination: String = Screen.Login.route) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Signup.route) { SignupScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.TutorDetail.route) { TutorDetailScreen(navController) }
        composable(Screen.PostJob.route) { PostJobScreen(navController) }
    }
}