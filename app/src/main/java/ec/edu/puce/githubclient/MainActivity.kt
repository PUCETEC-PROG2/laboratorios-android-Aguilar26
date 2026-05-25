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
import ec.edu.puce.githubclient.ui.screens.CreateRepoScreen
import ec.edu.puce.githubclient.ui.screens.RepoList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var showForm by remember { mutableStateOf(false) }

                    if (showForm) {
                        CreateRepoScreen(
                            onRepoCreated = { showForm = false }
                        )
                    } else {
                        RepoList(
                            onCreateClick = { showForm = true }
                        )
                    }
                }
            }
        }
    }
}