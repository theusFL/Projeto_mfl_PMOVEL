package com.example.projeto_mfl_pmovel.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projeto_mfl_pmovel.data.model.Post
import com.example.projeto_mfl_pmovel.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FeedUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false
)

class FeedViewModel(private val repository: PostRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedUiState(isLoading = true))
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.posts.collect { posts ->
                _uiState.value = _uiState.value.copy(posts = posts, isLoading = false)
            }
        }
        viewModelScope.launch {
            repository.refreshPosts()
        }
    }

    fun toggleLike(post: Post) {
        viewModelScope.launch { repository.toggleLike(post) }
    }

    class Factory(private val repository: PostRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FeedViewModel(repository) as T
        }
    }
}
