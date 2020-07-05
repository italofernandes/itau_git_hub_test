package br.com.itau.github.data.datasource

import br.com.itau.github.data.model.PrResponseModel
import br.com.itau.github.data.model.RepoListResponseModel
import br.com.itau.github.data.service.GitHubService
import io.reactivex.Single

interface GitHubDataSource{
    fun getAllRepository(page: Long): Single<RepoListResponseModel>
    fun getPrsForRepository(owner: String, repoName: String): Single<List<PrResponseModel>>
}

class GitHubWebDataSource(
    private val service: GitHubService
): GitHubDataSource{

    override fun getAllRepository(page: Long): Single<RepoListResponseModel> = service.getRepositories(page).firstOrError()

    override fun getPrsForRepository(
        owner: String,
        repoName: String
    ): Single<List<PrResponseModel>> {
        return  service.getPrs(owner, repoName).firstOrError()
    }
}
