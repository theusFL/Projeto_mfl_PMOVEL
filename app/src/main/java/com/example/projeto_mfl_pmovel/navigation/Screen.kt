package com.example.projeto_mfl_pmovel.navigation

sealed class Screen(val route: String) {
    object Feed : Screen("feed_screen")
}
