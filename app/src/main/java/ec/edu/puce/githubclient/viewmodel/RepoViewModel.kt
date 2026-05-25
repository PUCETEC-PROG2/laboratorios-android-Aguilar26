package ec.edu.puce.githubclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.puce.githubclient.BuildConfig
import ec.edu.puce.githubclient.model.RepoModel
import ec.edu.puce.githubclient.network.CreateRepoRequest
import ec.edu.puce.githubclient.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RepoUiState {
    object Loading : RepoUiState()
    data class Success(val repos: List<RepoModel>) : RepoUiState()
    data class Error(val message: String) : RepoUiState()
}

sealed class CreateRepoState {
    object Idle : CreateRepoState()
    object Loading : CreateRepoState()
    object Success : CreateRepoState()
    data class Error(val message: String) : CreateRepoState()
}

class RepoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<RepoUiState>(RepoUiState.Loading)
    val uiState: StateFlow<RepoUiState> = _uiState

    private val _createState = MutableStateFlow<CreateRepoState>(CreateRepoState.Idle)
    val createState: StateFlow<CreateRepoState> = _createState

    init {
        fetchRepos()
    }

    private fun fetchRepos() {
        viewModelScope.launch {
            _uiState.value = RepoUiState.Loading
            try {
                val token = "Bearer ${BuildConfig.GITHUB_TOKEN}"
                val repos = RetrofitInstance.api.getMyRepos(token)
                _uiState.value = RepoUiState.Success(repos)
            } catch (e: Exception) {
                _uiState.value = RepoUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun createRepo(name: String, description: String) {
        viewModelScope.launch {
            _createState.value = CreateRepoState.Loading
            try {
                val token = "Bearer ${BuildConfig.GITHUB_TOKEN}"
                RetrofitInstance.api.createRepo(
                    token = token,
                    body = CreateRepoRequest(name = name, description = description)
                )
                _createState.value = CreateRepoState.Success
                fetchRepos()
            } catch (e: Exception) {
                _createState.value = CreateRepoState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun resetCreateState() {
        _createState.value = CreateRepoState.Idle
    }
}