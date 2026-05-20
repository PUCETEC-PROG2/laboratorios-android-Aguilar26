package ec.edu.puce.githubclient.network

import ec.edu.puce.githubclient.model.RepoModel
import retrofit2.http.GET
import retrofit2.http.Header

interface GitHubApiService {
    @GET("user/repos")
    suspend fun getMyRepos(
        @Header("Authorization") token: String
    ): List<RepoModel>
}