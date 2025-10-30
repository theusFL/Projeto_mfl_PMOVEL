package com.example.projeto_mfl_pmovel.ui.upload

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

@Composable
fun UploadScreen(onPostUploaded: () -> Unit) {
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    val storage = FirebaseStorage.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    // Lançador para pegar imagem da galeria
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    fun uploadPost() {
        if (imageUri == null || isLoading) return
        isLoading = true

        val userId = auth.currentUser?.uid ?: return
        val imageFileName = "${UUID.randomUUID()}.jpg"
        val storageRef = storage.reference.child("images/$imageFileName")

        // 1. Upload da Imagem para o Storage
        storageRef.putFile(imageUri!!)
            .addOnSuccessListener {
                // 2. Pegar a URL de Download
                storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    // 3. Criar o objeto Post
                    val post = Post(
                        id = UUID.randomUUID().toString(),
                        userId = userId,
                        username = auth.currentUser?.email?.split("@")?.get(0) ?: "Anônimo",
                        imageUrl = downloadUrl.toString(),
                        description = description,
                        likes = 0
                    )

                    // 4. Salvar o Post no Firestore
                    firestore.collection("posts").add(post)
                        .addOnSuccessListener {
                            isLoading = false
                            onPostUploaded() // Navega de volta ao feed
                        }
                        .addOnFailureListener {
                            isLoading = false
                            // Tratar erro
                        }
                }
            }
            .addOnFailureListener {
                isLoading = false
                // Tratar erro de upload
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Selecionar Imagem")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Imagem selecionada",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(onClick = { uploadPost() }, enabled = imageUri != null) {
                Text("Postar")
            }
        }
    }
}