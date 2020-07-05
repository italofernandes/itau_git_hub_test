package br.com.itau.github.domain.usecase.list

import br.com.itau.github.data.repository.GitHubRepository
import br.com.itau.github.common.domain.usecase.AppSchedulers
import br.com.itau.github.common.domain.usecase.BaseUseCase
import br.com.itau.github.data.model.RepoListResponseModel
import br.com.itau.github.domain.entity.RepoListEntity
import br.com.itau.github.domain.entity.RepoEntity

class RepoListUseCase(
    private val repository: GitHubRepository,
    private val schedulers: AppSchedulers
): BaseUseCase() {

    fun getAllRepositories(page: Long,
                           success: ((RepoListEntity) -> Unit)?,
                           error:((Int)->Unit)?
    ){
        addDisposable(repository
        .getAllRepository(page)
        .subscribeOn(schedulers.worker())
        .observeOn(schedulers.main())
        .subscribe({repositories->
            success?.invoke(transform(repositories))
        }, {exception ->
            error?.invoke(handleException(exception))
        }))
    }

    private fun transform(response: RepoListResponseModel?): RepoListEntity =
      RepoListEntity(
          total = response?.totalCount ?: 0,
          repositories = response?.items?.map {repo->
              RepoEntity(
                  name = repo.nameRepo ?: "",
                  description = repo.description ?: "",
                  author = repo.owner?.username ?: "",
                  authorImage =  repo.owner?.userImgUrl ?: "",
                  starsNumber = repo.starsNumber ?: 0,
                  forkNumber = repo.forksNumbers ?: 0
              )
          } ?: listOf()
      )
}