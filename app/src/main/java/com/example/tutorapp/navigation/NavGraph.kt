package com.example.tutorapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tutorapp.screen.admin.AdminDashboardScreen
import com.example.tutorapp.screen.home.HomeScreen
import com.example.tutorapp.screen.login.LoginScreen
import com.example.tutorapp.screen.signup.SignupScreen
import com.example.tutorapp.screen.tutordetail.TutorDetailScreen
import com.example.tutorapp.screen.register.RegisterClassScreen
import com.example.tutorapp.screen.changepassword.ChangePasswordScreen
import com.example.tutorapp.screen.classlist.ClassListScreen
import com.example.tutorapp.screen.success.RegisterSuccessScreen
import com.example.tutorapp.screen.search.TutorSearchScreen
import com.example.tutorapp.screen.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object ChangePassword : Screen("change_password")
    object ClassList : Screen("class_list")
    object TutorDetail : Screen("tutor_detail/{tutorId}") // Route có biến
    object RegisterClass : Screen("register_class/{tutorId}")
    object TutorSearch : Screen("search_tutor")
    object RegisterSuccess : Screen("register_success")


}

@Composable
fun NavGraph(navController: NavHostController, startDestination: String = Screen.Login.route) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Signup.route) { SignupScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.ChangePassword.route) { ChangePasswordScreen(navController) }
        composable(Screen.ClassList.route) { ClassListScreen(navController) }
        composable(Screen.TutorSearch.route) { TutorSearchScreen(navController) }
        composable(Screen.RegisterSuccess.route) { RegisterSuccessScreen(navController) }
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable("admin_dashboard") {
            AdminDashboardScreen(navController = navController)
        }
        // ✅ Màn TutorDetail nhận tutorId
        composable(
            route = "tutor_detail/{tutorId}",
            arguments = listOf(navArgument("tutorId") { defaultValue = "" })
        ) { backStackEntry ->
            val tutorId = backStackEntry.arguments?.getString("tutorId") ?: ""
            TutorDetailScreen(navController = navController, tutorId = tutorId)
        }
        composable(
            route = "register_class/{tutorId}",
            arguments = listOf(navArgument("tutorId") { defaultValue = "" })
        ) { backStackEntry ->
            val tutorId = backStackEntry.arguments?.getString("tutorId") ?: ""
            RegisterClassScreen(navController = navController, tutorId = tutorId)
        }
    }
}
