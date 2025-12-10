package com.example.projeto_mfl_pmovel.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projeto_mfl_pmovel.data.repository.PostRepository
import com.example.projeto_mfl_pmovel.ui.auth.LoginScreen
import com.example.projeto_mfl_pmovel.ui.feed.FeedScreen
import com.example.projeto_mfl_pmovel.ui.feed.FeedViewModel

@Composable
fun AppNavigation(repository: PostRepository) {
    val navController = rememberNavController()
    val startDestination = Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Screen.Feed.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Feed.route) {
            val viewModel: FeedViewModel = viewModel(
                factory = FeedViewModel.Factory(repository)
            )
            FeedScreen(viewModel = viewModel)
        }
    }
}
