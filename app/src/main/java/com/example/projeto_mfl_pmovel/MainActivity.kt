package com.example.projeto_mfl_pmovel

// MainActivity.kt (Modificado)
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.your.package.name.ui.theme.YourAppTheme // Importe seu tema

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YourAppTheme { // Aplica o tema
                AppNavigation() // Chama o controlador de navegação
            }
        }
    }
}