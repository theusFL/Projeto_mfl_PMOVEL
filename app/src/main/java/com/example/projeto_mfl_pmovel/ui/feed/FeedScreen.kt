package com.example.projeto_mfl_pmovel.ui.feed


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier, // Recebe o modifier (com padding) do AppNavigation
    viewModel: FeedViewModel = viewModel()
) {
    // Observa o novo uiState
    val uiState by viewModel.uiState.collectAsState()

    // BÔNUS: Lida com os 3 estados da UI
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }
            is UiState.Success -> {
                val posts = state.data
                if (posts.isEmpty()) {
                    Text("Nenhuma postagem ainda...")
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(posts) { post ->
                            // Passa o viewModel e o post
                            PostItem(post = post, viewModel = viewModel)
                        }
                    }
                }
            }
            is UiState.Error -> {
                Text(
                    text = "Falha ao carregar posts: ${state.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun PostItem(post: Post, viewModel: FeedViewModel) { // Recebe o ViewModel
    val auth = FirebaseAuth.getInstance()
    // Verifica se o usuário atual curtiu este post
    val isLiked = post.likedBy.contains(auth.currentUser?.uid)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = post.imageUrl,
                contentDescription = post.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = post.username,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = post.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Lógica do Botão de Like
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        // Chama a função do ViewModel!
                        viewModel.toggleLike(post.id, post.likedBy)
                    }) {
                        Icon(
                            // Muda o ícone baseado no 'isLiked'
                            imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            // Muda a cor baseado no 'isLiked'
                            tint = if (isLiked) Color.Red else LocalContentColor.current
                        )
                    }
                    // Mostra o total de likes baseado no tamanho da lista
                    Text("${post.likedBy.size} likes")
                }
            }
        }
    }
}