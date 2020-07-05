package br.com.itau.github.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class RepoListEntity(
    val total: Long,
    val repositories: List<RepoEntity>
)

@Parcelize
data class RepoEntity(
    val name: String,
    val description: String,
    val author: String,
    val authorImage: String,
    val starsNumber: Long,
    val forkNumber: Long
):Parcelable