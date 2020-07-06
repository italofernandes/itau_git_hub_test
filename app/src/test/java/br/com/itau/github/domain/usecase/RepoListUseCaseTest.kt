package br.com.itau.github.domain.usecase

import br.com.itau.github.R
import br.com.itau.github.TestSchedulers
import br.com.itau.github.data.repository.GitHubRepository
import br.com.itau.github.domain.entity.RepoListEntity
import br.com.itau.github.domain.usecase.list.RepoListUseCase
import br.com.itau.github.mockRepoListEntityUseCase
import br.com.itau.github.mockRepoResponseModel
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
class RepoListUseCaseTest{

    @Mock
    private lateinit var repository: GitHubRepository
    private lateinit var useCase: RepoListUseCase

    @Before
    fun init(){
        useCase = RepoListUseCase(repository, TestSchedulers())
    }

    @Test
    fun `test get all repositories success`(){
        //given
        val page = 1L
        val result = mockRepoListEntityUseCase
        whenever(repository.getAllRepository(any())).thenReturn(Single.just(mockRepoResponseModel))
        val future = CompletableFuture<RepoListEntity>()

        //when
        useCase.getAllRepositories(page, {
            future.complete(it)
        }, { Assert.fail() })
        
        // then
        Assert.assertEquals(result, future.get())
        verify(repository).getAllRepository(page)

    }

    @Test
    fun `test get all repositories generic error`(){
        //Given
        whenever(repository.getAllRepository(any())).doReturn(Single.error(Exception("Error")))
        val future = CompletableFuture<Int>()

        //When
        useCase.getAllRepositories(1L, { Assert.fail() }, {
            future.complete(it)
        })

        // Then:
        Assert.assertEquals(R.string.service_generic_error_msg, future.get())
        verify(repository).getAllRepository(1L)
   }

    @Test
    fun `test get all repositories connection error`(){
        //Given
        whenever(repository.getAllRepository(any())).doReturn(Single.error(SocketTimeoutException("Error")))
        val future = CompletableFuture<Int>()

        //When
        useCase.getAllRepositories(1L, { Assert.fail() }, {
            future.complete(it)
        })

        // Then:
        Assert.assertEquals(R.string.service_conn_error_msg, future.get())
        verify(repository).getAllRepository(1L)
    }
}

