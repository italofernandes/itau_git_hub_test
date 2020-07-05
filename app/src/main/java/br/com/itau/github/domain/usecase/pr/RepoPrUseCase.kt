package br.com.itau.github.domain.usecase.pr

import br.com.itau.github.common.domain.usecase.AppSchedulers
import br.com.itau.github.common.domain.usecase.BaseUseCase
import br.com.itau.github.data.model.PrResponseModel
import br.com.itau.github.data.repository.GitHubRepository
import br.com.itau.github.domain.entity.PrEntity

class RepoPrUseCase(
    private val repository: GitHubRepository,
    private val schedulers: AppSchedulers
): BaseUseCase(){

    fun getAllPrs(owner: String,
                  repoName: String,
                  success: ((List<PrEntity>) -> Unit)?,
                  error:((Int)->Unit)?
    ){
        addDisposable(repository
            .getPrsForRepository(owner, repoName)
            .subscribeOn(schedulers.worker())
            .observeOn(schedulers.main())
            .map { transform(it) }
            .subscribe({repositories->
                success?.invoke(repositories)
            }, {exception ->
                error?.invoke(handleException(exception))
            }))
    }

    private fun transform(prList: List<PrResponseModel>?): List<PrEntity> =
        prList?.map { prResp ->
            PrEntity(
                title = prResp.title ?: "",
                body = prResp.body ?: "",
                date = prResp.createdDate ?: "",
                authorName = prResp.user?.username ?: "",
                authorImage = prResp.user?.avatarUrl ?: ""
            )
        } ?: run { arrayListOf<PrEntity>() }
}