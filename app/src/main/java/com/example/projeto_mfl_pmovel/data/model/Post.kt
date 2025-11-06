package com.example.projeto_mfl_pmovel.data.model

data class Post(
    val id: String,
    val username: String,
    val imageUrl: String,
    val description: String,
    val likes: Int,
    val isLiked: Boolean = false // Para controlar o estado do like localmente
)
