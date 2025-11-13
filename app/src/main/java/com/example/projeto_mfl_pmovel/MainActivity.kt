package com.example.projeto_mfl_pmovel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.projeto_mfl_pmovel.ui.theme.Projeto_mfl_PMOVELTheme
import com.example.projeto_mfl_pmovel.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projeto_mfl_PMOVELTheme {
                AppNavigation()
            }
        }
    }
}