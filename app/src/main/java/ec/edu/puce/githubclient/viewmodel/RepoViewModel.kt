package ec.edu.puce.githubclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.puce.githubclient.BuildConfig
import ec.edu.puce.githubclient.model.RepoModel
import ec.edu.puce.githubclient.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RepoUiState {
    object Loading : RepoUiState()
    data class Success(val repos: List<RepoModel>) : RepoUiState()
    data class Error(val message: String) : RepoUiState()
}

class RepoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<RepoUiState>(RepoUiState.Loading)
    val uiState: StateFlow<RepoUiState> = _uiState

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
}