package br.com.itau.github.di

import br.com.itau.github.domain.usecase.list.RepoListUseCase
import br.com.itau.github.presentation.repoList.viewmodel.RepoListViewModel
import com.google.gson.Gson
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class RepoListDiTest : AutoCloseKoinTest() {

    @Before
    fun init() {
        startKoin{ modules(
            retrofitModule,
            repoModule,
            schedulerModule,
            listModel
        ) }
    }

    @Test
    fun `assert use case`(){
        val useCase by inject<RepoListUseCase>()
        assertNotNull(useCase)
    }

    @Test
    fun `assert viewmodel`(){
        val viewModel by inject<RepoListViewModel>()
        assertNotNull(viewModel)
    }

    @Test
    fun `init module test`(){
        val module = loadListDependencies()
        assertNotNull(module)
    }
}