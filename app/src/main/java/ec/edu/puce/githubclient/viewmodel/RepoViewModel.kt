package ec.edu.puce.githubclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.puce.githubclient.BuildConfig
import ec.edu.puce.githubclient.model.RepoModel
import ec.edu.puce.githubclient.model.UpdateRepoRequest
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

sealed class UpdateRepoState {
    object Idle : UpdateRepoState()
    object Loading : UpdateRepoState()
    object Success : UpdateRepoState()
    data class Error(val message: String) : UpdateRepoState()
}

sealed class DeleteRepoState {
    object Idle : DeleteRepoState()
    object Loading : DeleteRepoState()
    object Success : DeleteRepoState()
    data class Error(val message: String) : DeleteRepoState()
}

class RepoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<RepoUiState>(RepoUiState.Loading)
    val uiState: StateFlow<RepoUiState> = _uiState

    private val _createState = MutableStateFlow<CreateRepoState>(CreateRepoState.Idle)
    val createState: StateFlow<CreateRepoState> = _createState

    private val _updateState = MutableStateFlow<UpdateRepoState>(UpdateRepoState.Idle)
    val updateState: StateFlow<UpdateRepoState> = _updateState

    private val _deleteState = MutableStateFlow<DeleteRepoState>(DeleteRepoState.Idle)
    val deleteState: StateFlow<DeleteRepoState> = _deleteState

    init {
        fetchRepos()
    }

    fun fetchRepos() {
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

    fun updateRepo(owner: String, repoName: String, newName: String, description: String) {
        viewModelScope.launch {
            _updateState.value = UpdateRepoState.Loading
            try {
                val token = "Bearer ${BuildConfig.GITHUB_TOKEN}"
                RetrofitInstance.api.updateRepo(
                    token = token,
                    owner = owner,
                    repoName = repoName,
                    body = UpdateRepoRequest(name = newName, description = description)
                )
                _updateState.value = UpdateRepoState.Success
                fetchRepos()
            } catch (e: Exception) {
                _updateState.value = UpdateRepoState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun deleteRepo(owner: String, repoName: String) {
        viewModelScope.launch {
            _deleteState.value = DeleteRepoState.Loading
            try {
                val token = "Bearer ${BuildConfig.GITHUB_TOKEN}"
                RetrofitInstance.api.deleteRepo(
                    token = token,
                    owner = owner,
                    repoName = repoName
                )
                _deleteState.value = DeleteRepoState.Success
                fetchRepos()
            } catch (e: Exception) {
                _deleteState.value = DeleteRepoState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun resetCreateState() {
        _createState.value = CreateRepoState.Idle
    }

    fun resetUpdateState() {
        _updateState.value = UpdateRepoState.Idle
    }

    fun resetDeleteState() {
        _deleteState.value = DeleteRepoState.Idle
    }
}