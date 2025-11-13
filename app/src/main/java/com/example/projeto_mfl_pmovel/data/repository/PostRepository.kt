package com.example.projeto_mfl_pmovel.data.repository

import com.example.projeto_mfl_pmovel.data.model.Post
import java.util.UUID

class PostRepository {

    // Nossa "base de dados" fake
    private val initialPosts = listOf(
        Post(
            id = UUID.randomUUID().toString(),
            username = "coelhoviaja",
            imageUrl = "https://picsum.photos/seed/picsum/400/300",
            description = "Primeira foto da viagem! ✈️",
            likes = 12
        ),
        Post(
            id = UUID.randomUUID().toString(),
            username = "gato_gourmet",
            imageUrl = "https://picsum.photos/seed/food/400/300",
            description = "O que comemos hoje: lasanha.",
            likes = 42,
            isLiked = true
        ),
        Post(
            id = UUID.randomUUID().toString(),
            username = "android_dev",
            imageUrl = "https://picsum.photos/seed/code/400/300",
            description = "Trabalhando no meu novo app! #Compose",
            likes = 7
        )
    )

    fun getPosts(): List<Post> {
        return initialPosts
    }
}
