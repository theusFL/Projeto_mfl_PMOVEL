package com.example.projeto_mfl_pmovel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.projeto_mfl_pmovel.data.local.AppDatabase
import com.example.projeto_mfl_pmovel.data.remote.PostRemoteDataSource
import com.example.projeto_mfl_pmovel.data.repository.PostRepository
import com.example.projeto_mfl_pmovel.navigation.AppNavigation
import com.example.projeto_mfl_pmovel.ui.theme.Projeto_mfl_PMOVELTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()

        val remoteDataSource = PostRemoteDataSource()
        val repository = PostRepository(db.postDao(), remoteDataSource)

        setContent {
            Projeto_mfl_PMOVELTheme {
                AppNavigation(repository)
            }
        }
    }
}
