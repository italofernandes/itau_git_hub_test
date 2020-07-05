package br.com.itau.github.di

import androidx.annotation.VisibleForTesting
import br.com.itau.github.domain.usecase.list.RepoListUseCase
import br.com.itau.github.presentation.repoList.viewmodel.RepoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val listModel = module {
    factory { RepoListUseCase(get(), get()) }
    viewModel { RepoListViewModel(get()) }
}

@VisibleForTesting
private val repoListDependency by lazy { loadKoinModules(listModel) }
internal fun loadListDependencies() = repoListDependency