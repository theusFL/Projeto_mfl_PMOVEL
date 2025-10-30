package com.example.projeto_mfl_pmovel.navigation

// AppNavigation.kt (arquivo novo)

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()

    // Decide a tela inicial: Se o usuário já está logado, vai para o Feed, senão, para Auth
    val startDestination = if (auth.currentUser != null) {
        Screen.Feed.route
    } else {
        Screen.Auth.route
    }

    NavHost(navController = navController, startDestination = startDestination) {

        // Tela de Autenticação
        composable(Screen.Auth.route) {
            AuthScreen(
                onLoginSuccess = {
                    // Após o login, navega para o Feed e limpa a tela de Auth da pilha
                    navController.navigate(Screen.Feed.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        // Tela de Feed
        composable(Screen.Feed.route) {
            // Usamos um Scaffold para ter um layout com um botão flutuante
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        navController.navigate(Screen.Upload.route)
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Novo Post")
                    }
                }
            ) { paddingValues ->
                // Passamos o padding para a FeedScreen
                FeedScreen(modifier = Modifier.padding(paddingValues))
            }
        }

        // Tela de Upload
        composable(Screen.Upload.route) {
            UploadScreen(
                onPostUploaded = {
                    // Após o upload, simplesmente volta para a tela anterior (o Feed)
                    navController.popBackStack()
                }
            )
        }
    }
}