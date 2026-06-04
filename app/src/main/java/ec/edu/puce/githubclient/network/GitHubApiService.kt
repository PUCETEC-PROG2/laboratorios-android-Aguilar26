package ec.edu.puce.githubclient.network

import ec.edu.puce.githubclient.model.RepoModel
import ec.edu.puce.githubclient.model.UpdateRepoRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

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

    @PATCH("repos/{owner}/{repo}")
    suspend fun updateRepo(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repoName: String,
        @Body body: UpdateRepoRequest
    ): RepoModel

    @DELETE("repos/{owner}/{repo}")
    suspend fun deleteRepo(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repoName: String
    )
}

data class CreateRepoRequest(
    val name: String,
    val description: String,
    val private: Boolean = false
)