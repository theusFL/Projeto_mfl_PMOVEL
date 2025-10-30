package com.example.projeto_mfl_pmovel.ui.feed

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// BÔNUS: Criar uma classe para representar o estado da UI
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class FeedViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // BÔNUS: Mudar de _posts para _uiState
    private val _uiState = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Post>>> = _uiState.asStateFlow()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        _uiState.value = UiState.Loading // BÔNUS: Inicia como carregando

        firestore.collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, error ->

                // BÔNUS: Tratar erro
                if (error != null) {
                    _uiState.value = UiState.Error(error.localizedMessage ?: "Erro desconhecido")
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    // Mapeia os documentos, convertendo para Post E pegando o ID
                    val posts = snapshots.map { doc ->
                        doc.toObject(Post::class.java).copy(id = doc.id)
                    }
                    _uiState.value = UiState.Success(posts) // BÔNUS: Emite sucesso
                }
            }
    }

    // NOVA FUNÇÃO: toggleLike
    fun toggleLike(postId: String, currentLikedBy: List<String>) {
        val userId = auth.currentUser?.uid ?: return
        val postRef = firestore.collection("posts").document(postId)

        val isCurrentlyLiked = currentLikedBy.contains(userId)

        // Usamos FieldValue.arrayUnion para adicionar um item (sem duplicar)
        // e FieldValue.arrayRemove para removê-lo.
        val updateData = if (isCurrentlyLiked) {
            mapOf("likedBy" to FieldValue.arrayRemove(userId))
        } else {
            mapOf("likedBy" to FieldValue.arrayUnion(userId))
        }

        // Atualiza o documento no Firestore
        postRef.update(updateData).addOnFailureListener {
            // Tratar falha (ex: logar o erro)
        }
    }
}