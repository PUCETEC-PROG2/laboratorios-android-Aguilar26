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
    val avatarUrl: String
)