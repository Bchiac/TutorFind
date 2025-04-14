package com.example.tutorapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tutorapp.screen.home.HomeScreen
import com.example.tutorapp.screen.login.LoginScreen
import com.example.tutorapp.screen.signup.SignupScreen
import com.example.tutorapp.screen.tutordetail.TutorDetailScreen
import com.example.tutorapp.screen.register.RegisterClassScreen
import com.example.tutorapp.screen.profile.ProfileScreen
import com.example.tutorapp.screen.changepassword.ChangePasswordScreen
import com.example.tutorapp.screen.classlist.ClassListScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object ChangePassword : Screen("change_password")
    object ClassList : Screen("class_list")
    object TutorDetail : Screen("tutor_detail")
    object RegisterClass : Screen("register_class")
}

@Composable
fun NavGraph(navController: NavHostController, startDestination: String = Screen.Login.route) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Signup.route) { SignupScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.ChangePassword.route) { ChangePasswordScreen(navController) }
        composable(Screen.ClassList.route) { ClassListScreen(navController) }
        composable(Screen.TutorDetail.route) { TutorDetailScreen(navController) }
        composable(Screen.RegisterClass.route) { RegisterClassScreen(navController) }
    }
}
