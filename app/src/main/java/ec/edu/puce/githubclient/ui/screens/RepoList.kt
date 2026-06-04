package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.model.RepoModel
import ec.edu.puce.githubclient.ui.components.RepoItem
import ec.edu.puce.githubclient.viewmodel.RepoUiState
import ec.edu.puce.githubclient.viewmodel.RepoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoList(
    onCreateClick: () -> Unit,
    onEditClick: (RepoModel) -> Unit,
    viewModel: RepoViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Repositorios") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1565C0),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick,
                shape = CircleShape,
                containerColor = Color(0xFF1976D2),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Crear repositorio"
                )
            }
        }
    ) { paddingValues ->

        when (uiState) {
            is RepoUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is RepoUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
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
                PullToRefreshBox(
                    isRefreshing = false,
                    onRefresh = { viewModel.fetchRepos() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        items(repos) { repo ->
                            RepoItem(
                                name = repo.name,
                                avatarUrl = repo.owner.avatarUrl,
                                description = repo.description ?: "Sin descripción",
                                language = repo.language ?: "No especificado",
                                onEditClick = { onEditClick(repo) },
                                onDeleteClick = {
                                    viewModel.deleteRepo(
                                        owner = repo.owner.login,
                                        repoName = repo.name
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}