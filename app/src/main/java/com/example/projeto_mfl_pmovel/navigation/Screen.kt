package com.example.projeto_mfl_pmovel.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Feed : Screen("feed_screen")
}
