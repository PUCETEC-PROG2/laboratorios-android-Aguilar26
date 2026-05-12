package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ec.edu.puce.githubclient.ui.components.RepoItem

@Composable
fun RepoList() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        RepoItem(
            name = "Practica #1",
            avatarUrl = "https://i.pinimg.com/236x/6c/55/d4/6c55d49dd6839b5b79e84a1aa6d2260d.jpg",
            description = "Practica # 1",
            language = "Kotlin"
        )
        RepoItem(
            name = "Practica #2",
            avatarUrl = "https://i.pinimg.com/236x/6c/55/d4/6c55d49dd6839b5b79e84a1aa6d2260d.jpg",
            description = "Practica # 2",
            language = "Kotlin"
        )
        RepoItem(
            name = "Practica #3",
            avatarUrl = "https://i.pinimg.com/236x/6c/55/d4/6c55d49dd6839b5b79e84a1aa6d2260d.jpg",
            description = "Practica # 3",
            language = "Kotlin"
        )
        RepoItem(
            name = "Practica #4",
            avatarUrl = "https://i.pinimg.com/236x/6c/55/d4/6c55d49dd6839b5b79e84a1aa6d2260d.jpg",
            description = "Practica # 4",
            language = "Kotlin"
        )

    }
}