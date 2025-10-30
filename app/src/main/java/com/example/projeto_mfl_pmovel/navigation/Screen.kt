package com.example.projeto_mfl_pmovel.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth_screen")
    object Feed : Screen("feed_screen")
    object Upload : Screen("upload_screen")
}