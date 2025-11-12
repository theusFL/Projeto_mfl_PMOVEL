package com.example.projeto_mfl_pmovel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projeto_mfl_pmovel.ui.auth.LoginScreen 
import com.example.projeto_mfl_pmovel.ui.feed.FeedScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val startDestination = Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Screen.Feed.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.Feed.route) {
            FeedScreen()
        }
    }
}
