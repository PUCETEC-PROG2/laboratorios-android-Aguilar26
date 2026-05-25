package ec.edu.puce.githubclient.network

import ec.edu.puce.githubclient.model.RepoModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface GitHubApiService {
    @GET("user/repos")
    suspend fun getMyRepos(
        @Header("Authorization") token: String
    ): List<RepoModel>

    @POST("user/repos")
    suspend fun createRepo(
        @Header("Authorization") token: String,
        @Body body: CreateRepoRequest
    ): RepoModel
}

data class CreateRepoRequest(
    val name: String,
    val description: String,
    val private: Boolean = false
)