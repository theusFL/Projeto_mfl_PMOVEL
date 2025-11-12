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
import com.example.projeto_mfl_pmovel.data.model.Post

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val posts = uiState.posts

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center 
    ) {
        if (posts.isEmpty()) {
            Text("Nenhuma postagem ainda...")
        } else {
            LazyColumn(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxSize()
            ) {
                items(posts) { post ->
                    PostItem(
                        post = post,
                        onLikeClicked = { viewModel.toggleLike(post.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun PostItem(post: Post, onLikeClicked: () -> Unit) {

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
                    .aspectRatio(16f / 9f),
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

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onLikeClicked() }) {
                        Icon(
                            imageVector = if (post.isLiked) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (post.isLiked) Color.Red else LocalContentColor.current
                        )
                    }
                    Text("${post.likes} likes")
                }
            }
        }
    }
}
