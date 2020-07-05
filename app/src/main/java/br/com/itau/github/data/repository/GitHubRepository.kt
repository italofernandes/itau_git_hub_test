package br.com.itau.github.data.repository

import br.com.itau.github.data.datasource.GitHubDataSource
import br.com.itau.github.data.model.PrResponseModel
import br.com.itau.github.data.model.RepoListResponseModel
import io.reactivex.Single

interface GitHubRepository{
    fun getAllRepository(page: Long): Single<RepoListResponseModel>
    fun getPrsForRepository(owner: String, repoName: String): Single<List<PrResponseModel>>
}

class GitHubRepositoryImpl(
    private val serviceDataSource: GitHubDataSource
):GitHubRepository{

    override fun getAllRepository(page: Long): Single<RepoListResponseModel> {
        return serviceDataSource.getAllRepository(page)
    }

    override fun getPrsForRepository(
        owner: String,
        repoName: String
    ): Single<List<PrResponseModel>> {
        return serviceDataSource.getPrsForRepository(owner, repoName)
    }
}