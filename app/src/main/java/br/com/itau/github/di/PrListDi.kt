package br.com.itau.github.di

import androidx.annotation.VisibleForTesting
import br.com.itau.github.domain.usecase.pr.RepoPrUseCase
import br.com.itau.github.presentation.repoPrs.viewmodel.PrViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val prsModel = module {
    factory { RepoPrUseCase(get(), get()) }
    viewModel { PrViewModel(get()) }
}

@VisibleForTesting
private val repoPrsDependency by lazy { loadKoinModules(listOf(prsModel)) }
internal fun loadPrListDependencies() = repoPrsDependency