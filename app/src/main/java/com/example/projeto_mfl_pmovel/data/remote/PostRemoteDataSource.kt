package com.example.projeto_mfl_pmovel.data.remote

import com.example.projeto_mfl_pmovel.data.local.PostEntity
import kotlinx.coroutines.delay
import java.util.UUID

class PostRemoteDataSource {
    suspend fun fetchPostsFromApi(): List<PostEntity> {
        delay(2000) // Simula atraso da internet
        return listOf(
            PostEntity(
                id = UUID.randomUUID().toString(),
                username = "viajante_br",
                imageUrl = "https://picsum.photos/seed/travel/400/300",
                description = "Explorando o mundo! 🌎",
                likes = 120,
                isLiked = false
            ),
            PostEntity(
                id = UUID.randomUUID().toString(),
                username = "tech_lover",
                imageUrl = "https://picsum.photos/seed/tech/400/300",
                description = "Novo setup montado! 💻",
                likes = 45,
                isLiked = true
            ),
             PostEntity(
                id = UUID.randomUUID().toString(),
                username = "gatinhos_fofos",
                imageUrl = "https://picsum.photos/seed/cat/400/300",
                description = "Bom dia pessoal! 🐱",
                likes = 300,
                isLiked = false
            )
        )
    }
}
