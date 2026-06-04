package ec.edu.puce.githubclient.model

import com.google.gson.annotations.SerializedName

data class RepoModel(
    val id: Long,
    val name: String,
    val description: String?,
    val language: String?,
    @SerializedName("owner")
    val owner: Owner
)

data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("login")
    val login: String
)

data class CreateRepoRequest(
    val name: String,
    val description: String?,
    @SerializedName("private")
    val isPrivate: Boolean = false
)

data class UpdateRepoRequest(
    val name: String?,
    val description: String?
)