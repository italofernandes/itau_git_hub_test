package br.com.itau.github.di

import br.com.itau.github.domain.usecase.pr.RepoPrUseCase
import br.com.itau.github.presentation.repoPrs.viewmodel.PrViewModel
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class PrListDiTest: AutoCloseKoinTest() {

    @Before
    fun init() {
        startKoin{ modules(
            retrofitModule,
            repoModule,
            useCaseModule,
            schedulerModule,
            prsModel
        ) }
    }

    @Test
    fun `assert use case`(){
        val useCase by inject<RepoPrUseCase>()
        assertNotNull(useCase)
    }

    @Test
    fun `assert viewmodel`(){
        val viewModel by inject<PrViewModel>()
        assertNotNull(viewModel)
    }

    @Test
    fun `init module test`(){
        val module = loadPrListDependencies()
        assertNotNull(module)
    }
}