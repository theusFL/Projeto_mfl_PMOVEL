package com.example.projeto_mfl_pmovel.data.repository

import com.example.projeto_mfl_pmovel.data.local.PostDao
import com.example.projeto_mfl_pmovel.data.model.Post
import com.example.projeto_mfl_pmovel.data.remote.PostRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepository(
    private val dao: PostDao,
    private val remoteDataSource: PostRemoteDataSource
) {
    val posts: Flow<List<Post>> = dao.getAllPosts().map { entities ->
        entities.map { entity ->
            Post(entity.id, entity.username, entity.imageUrl, entity.description, entity.likes, entity.isLiked)
        }
    }

    suspend fun refreshPosts() {
        try {
            val remotePosts = remoteDataSource.fetchPostsFromApi()
            dao.insertAll(remotePosts)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun toggleLike(post: Post) {
        val newLikeState = !post.isLiked
        val newLikesCount = if (newLikeState) post.likes + 1 else post.likes - 1
        dao.updateLike(post.id, newLikeState, newLikesCount)
    }
}
