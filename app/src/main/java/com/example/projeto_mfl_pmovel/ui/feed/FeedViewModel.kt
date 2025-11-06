package com.example.projeto_mfl_pmovel.ui.feed

import androidx.lifecycle.ViewModel
import com.example.projeto_mfl_pmovel.data.model.Post
import com.example.projeto_mfl_pmovel.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FeedUiState(
    val posts: List<Post> = emptyList()
)

class FeedViewModel : ViewModel() {
    
    // API Fake
    private val repository = PostRepository()

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        val posts = repository.getPosts()
        _uiState.value = FeedUiState(posts = posts)
    }

    fun toggleLike(postId: String) {
        
        _uiState.update { currentState ->
            
            val newPosts = currentState.posts.map { post ->
                if (post.id != postId) {
                    post
                } else {
                    if (post.isLiked) {
                        post.copy(isLiked = false, likes = post.likes - 1)
                    } else {
                        post.copy(isLiked = true, likes = post.likes + 1)
                    }
                }
            }
            
            currentState.copy(posts = newPosts)
        }
    }
}
