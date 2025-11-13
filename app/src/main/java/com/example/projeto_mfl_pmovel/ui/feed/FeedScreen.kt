package com.example.projeto_mfl_pmovel.ui.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projeto_mfl_pmovel.data.model.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val posts = uiState.posts

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MathComunity") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Logo do App",
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
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
}