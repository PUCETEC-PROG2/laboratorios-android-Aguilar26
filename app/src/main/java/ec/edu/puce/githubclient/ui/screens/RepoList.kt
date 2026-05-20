package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.ui.components.RepoItem
import ec.edu.puce.githubclient.viewmodel.RepoUiState
import ec.edu.puce.githubclient.viewmodel.RepoViewModel

@Composable
fun RepoList(viewModel: RepoViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is RepoUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is RepoUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${(uiState as RepoUiState.Error).message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is RepoUiState.Success -> {
            val repos = (uiState as RepoUiState.Success).repos
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                items(repos) { repo ->
                    RepoItem(
                        name = repo.name,
                        avatarUrl = repo.owner.avatarUrl,
                        description = repo.description ?: "Sin descripción",
                        language = repo.language ?: "No especificado"
                    )
                }
            }
        }
    }
}