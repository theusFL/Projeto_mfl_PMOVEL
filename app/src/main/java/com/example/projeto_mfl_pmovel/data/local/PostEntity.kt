package com.example.projeto_mfl_pmovel.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val username: String,
    val imageUrl: String,
    val description: String,
    val likes: Int,
    val isLiked: Boolean
)
