package com.alexparrreira.personalproject_rpgficha

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexparrreira.personalproject_rpgficha.home.Home
import com.alexparrreira.personalproject_rpgficha.login.Login
import com.alexparrreira.personalproject_rpgficha.login.LoginViewModel
import com.alexparrreira.personalproject_rpgficha.login.SingUpSreen

enum class LoginRoutes{
    Signup,
    SignIn
}
enum class HomeRoutes{
    Home,
    Detail
}


@Composable
fun Navigation(
    videoUri: Uri,
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel
){
    NavHost(
        navController = navController,
        startDestination = LoginRoutes.SignIn.name
    ){
        composable(route = LoginRoutes.SignIn.name){
            Login(
                videoUri = videoUri,
                onNavToHomePage = {
                    navController.navigate(HomeRoutes.Home.name){
                        launchSingleTop = true
                        popUpTo(route = LoginRoutes.SignIn.name){
                            inclusive = true
                        }
                    }
                },
            loginViewModel = loginViewModel
                ) {
                    navController.navigate(LoginRoutes.Signup.name){
                        launchSingleTop = true
                        popUpTo(LoginRoutes.Signup.name){
                            inclusive = true
                        }
                    }
            }
        }

        composable(route = LoginRoutes.Signup.name){
            SingUpSreen(
                videoUri = videoUri,
                onNavToHomePage = {
                navController.navigate(HomeRoutes.Home.name){
                    popUpTo(LoginRoutes.Signup.name){
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignIn.name)
            }
        }

        composable( route = HomeRoutes.Home.name){
            Home()
        }
    }

}