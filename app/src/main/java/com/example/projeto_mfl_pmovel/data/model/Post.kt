package com.example.projeto_mfl_pmovel.data.model

// Post.kt (Modificado)
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Post(
    val id: String = "", // Manteremos o ID do documento aqui
    val userId: String = "",
    val username: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val likedBy: List<String> = emptyList(), // Lista de IDs de usuários que curtiram
    @ServerTimestamp
    val timestamp: Date? = null
)