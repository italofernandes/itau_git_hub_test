package br.com.itau.github.di

import br.com.itau.github.common.domain.usecase.AppSchedulers
import br.com.itau.github.data.datasource.GitHubDataSource
import br.com.itau.github.data.repository.GitHubRepository
import br.com.itau.github.data.service.GitHubService
import com.google.gson.Gson
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class ServiceDiTest : AutoCloseKoinTest() {

    @Before
    fun init(){
        startKoin{ modules(
            retrofitModule,
            repoModule,
            useCaseModule,
            schedulerModule
        ) }
    }

    @Test
    fun `assert gson`(){
        val gson by inject<Gson>()
        assertNotNull(gson)
    }

    @Test
    fun `assert service`(){
        val service by inject<GitHubService>()
        assertNotNull(service)
    }

    @Test
    fun `assert datasource`(){
        val dataSource by inject<GitHubDataSource>()
        assertNotNull(dataSource)
    }

    @Test
    fun `assert reposistory`(){
        val repo by inject<GitHubRepository>()
        assertNotNull(repo)
    }

    @Test
    fun `assert schedulers`(){
        val schedulers by inject<AppSchedulers>()
        assertNotNull(schedulers)
    }
}