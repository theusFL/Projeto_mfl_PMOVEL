package com.example.projeto_mfl_pmovel.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("UPDATE posts SET isLiked = :isLiked, likes = :likes WHERE id = :id")
    suspend fun updateLike(id: String, isLiked: Boolean, likes: Int)
}
