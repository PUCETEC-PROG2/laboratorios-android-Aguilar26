package ec.edu.puce.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ec.edu.puce.githubclient.model.RepoModel
import ec.edu.puce.githubclient.ui.screens.CreateRepoScreen
import ec.edu.puce.githubclient.ui.screens.EditRepoScreen
import ec.edu.puce.githubclient.ui.screens.RepoList

sealed class Screen {
    object RepoList : Screen()
    object CreateRepo : Screen()
    data class EditRepo(val repo: RepoModel) : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf<Screen>(Screen.RepoList) }

                    when (currentScreen) {
                        is Screen.RepoList -> {
                            RepoList(
                                onCreateClick = { currentScreen = Screen.CreateRepo },
                                onEditClick = { repo -> currentScreen = Screen.EditRepo(repo) }
                            )
                        }
                        is Screen.CreateRepo -> {
                            CreateRepoScreen(
                                onRepoCreated = { currentScreen = Screen.RepoList },
                                onNavigateBack = { currentScreen = Screen.RepoList }
                            )
                        }
                        is Screen.EditRepo -> {
                            val repo = (currentScreen as Screen.EditRepo).repo
                            EditRepoScreen(
                                repo = repo,
                                onRepoUpdated = { currentScreen = Screen.RepoList },
                                onNavigateBack = { currentScreen = Screen.RepoList }
                            )
                        }
                    }
                }
            }
        }
    }
}