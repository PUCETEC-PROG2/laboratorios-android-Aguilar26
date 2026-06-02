package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.viewmodel.CreateRepoState
import ec.edu.puce.githubclient.viewmodel.RepoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoForm(onNavigateBack: () -> Unit, content: @Composable (Modifier) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Repositorio") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        // Aquí se le aplica el padding correcto al contenido que viene de CreateRepoScreen
        content(Modifier.padding(paddingValues))
    }
}

@Composable
fun CreateRepoScreen(
    onRepoCreated: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: RepoViewModel = viewModel()
) {
    val createState by viewModel.createState.collectAsState()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(createState) {
        if (createState is CreateRepoState.Success) {
            viewModel.resetCreateState()
            onRepoCreated()
        }
    }

    // Envolvemos el diseño original dentro de tu RepoForm heredando los modificadores de espacio
    RepoForm(onNavigateBack = onNavigateBack) { scaffoldModifier ->
        Box(modifier = Modifier.fillMaxSize().then(scaffoldModifier)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crear Repositorio",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre del repositorio") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                if (createState is CreateRepoState.Loading) {
                    Spacer(modifier = Modifier.height(24.dp))
                    CircularProgressIndicator()
                }

                if (createState is CreateRepoState.Error) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = (createState as CreateRepoState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            FloatingActionButton(
                onClick = { if (name.isNotBlank()) viewModel.createRepo(name, description) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
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
    }
}