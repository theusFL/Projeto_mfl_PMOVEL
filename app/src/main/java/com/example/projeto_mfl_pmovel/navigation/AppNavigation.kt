package com.example.projeto_mfl_pmovel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projeto_mfl_pmovel.ui.feed.FeedScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val startDestination = Screen.Feed.route

    NavHost(navController = navController, startDestination = startDestination) {

        // Tela de Feed (única tela)
        composable(Screen.Feed.route) {
            FeedScreen()
        }
    }
}
