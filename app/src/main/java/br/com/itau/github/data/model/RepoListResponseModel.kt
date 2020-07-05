package br.com.itau.github.data.model

import com.google.gson.annotations.SerializedName

data class RepoListResponseModel(
    @SerializedName("total_count") val totalCount: Long? = null,
    @SerializedName("items") val items: List<Repository>? = null
)

data class Repository(
    @SerializedName("id") val id: Long?,
    @SerializedName("name") val nameRepo: String?,
    @SerializedName("full_name") val fullNameRepo: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("forks_count") val forksNumbers: Long?,
    @SerializedName("stargazers_count") val starsNumber: Long?,
    @SerializedName("owner") val owner: Owner?
)

data class Owner(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("login") val username: String? = null,
    @SerializedName("avatar_url") val userImgUrl: String? = null
)