package br.com.itau.github.domain.usecase

import br.com.itau.github.R
import br.com.itau.github.TestSchedulers
import br.com.itau.github.data.repository.GitHubRepository
import br.com.itau.github.domain.entity.PrEntity
import br.com.itau.github.domain.usecase.pr.RepoPrUseCase
import br.com.itau.github.mockPrModel
import br.com.itau.github.mockUserCasePrEntity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.net.SocketTimeoutException
import java.util.concurrent.CompletableFuture

@RunWith(MockitoJUnitRunner::class)
class RepoPrUseCaseTest {
    @Mock
    private lateinit var repository: GitHubRepository
    private lateinit var useCase: RepoPrUseCase

    @Before
    fun init(){
        useCase = RepoPrUseCase(repository, TestSchedulers())
    }

    @Test
    fun `test get all prs for repo success`(){
        //given
        val owner = "test"
        val repos = "repo"
        val result = listOf(mockUserCasePrEntity, mockUserCasePrEntity)

        whenever(repository.getPrsForRepository(any(), any())).thenReturn(Single.just(listOf(mockPrModel, mockPrModel)))
        val future = CompletableFuture<List<PrEntity>>()

        //when
        useCase.getAllPrs(owner, repos, {
            future.complete(it)
        }, { Assert.fail() })

        // then
        Assert.assertEquals(result, future.get())
        verify(repository).getPrsForRepository(owner, repos)
    }

    @Test
    fun `test get all prs for repo generic error`(){
        //Given
        val owner = "test"
        val repos = "repo"
        whenever(repository.getPrsForRepository(any(), any())).doReturn(Single.error(Exception("Error")))
        val future = CompletableFuture<Int>()

        //When
        useCase.getAllPrs(owner, repos, { Assert.fail() }, {
            future.complete(it)
        })

        // Then:
        Assert.assertEquals(R.string.service_generic_error_msg, future.get())
        verify(repository).getPrsForRepository(owner, repos)
    }

    @Test
    fun `test get all prs for repo connection error`(){
        //Given
        val owner = "test"
        val repos = "repo"
        whenever(repository.getPrsForRepository(any(), any())).doReturn(Single.error(SocketTimeoutException("Error")))
        val future = CompletableFuture<Int>()

        //When
        useCase.getAllPrs(owner, repos, { Assert.fail() }, {
            future.complete(it)
        })

        // Then:
        Assert.assertEquals(R.string.service_conn_error_msg, future.get())
        verify(repository).getPrsForRepository(owner, repos)
    }
}