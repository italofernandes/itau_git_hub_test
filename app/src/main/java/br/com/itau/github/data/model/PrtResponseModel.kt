package br.com.itau.github.data.model

import com.google.gson.annotations.SerializedName

data class PrResponseModel(
    @SerializedName("number") val number: Long?,
    @SerializedName("state") val state: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("body") val body: String?,
    @SerializedName("created_at") val createdDate: String?,
    @SerializedName("updated_at") val updatedDate: String?,
    @SerializedName("user") val user: User? = null
)

data class User(
    @SerializedName("login") val username: String?,
    @SerializedName("avatar_url") val avatarUrl: String?
)