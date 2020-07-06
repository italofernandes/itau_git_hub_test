package br.com.itau.github.data.repository

import br.com.itau.github.data.datasource.GitHubDataSource
import br.com.itau.github.data.model.PrResponseModel
import br.com.itau.github.data.model.RepoListResponseModel
import br.com.itau.github.mockPrModel
import br.com.itau.github.mockRepoResponseModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GitHubRepositoryTest(){

    @Mock
    private lateinit var dataSource: GitHubDataSource
    private lateinit var repository: GitHubRepository

    @Before
    fun init(){
        repository = GitHubRepositoryImpl(dataSource)
    }

    @Test
    fun `Test get all repositories`(){
        //given
        val page = 1L
        val result = mockRepoResponseModel

        whenever(dataSource.getAllRepository(page)).thenReturn(Single.just(result))
        val testObserver = TestObserver<RepoListResponseModel>()

        //when
        val request = dataSource.getAllRepository(page)
        request.subscribeWith(testObserver)

        //then
        testObserver.assertResult(result)
        verify(dataSource).getAllRepository(page)
        testObserver.dispose()
    }

    @Test
    fun `Test get all repositories Error`(){
        //given
        val page = 1L
        val result = Exception("Error Request")

        whenever(dataSource.getAllRepository(page)).thenReturn(Single.error(result))
        val testObserver = TestObserver<RepoListResponseModel>()

        //when
        val request = dataSource.getAllRepository(page)
        request.subscribeWith(testObserver)

        //then
        val errorCount = testObserver.errorCount()
        assertEquals(1, errorCount)
        verify(dataSource).getAllRepository(page)

        testObserver.dispose()
    }

    @Test
    fun `Test get all Prs`(){
        //given
        val owner = "test"
        val repo = "repo_test"
        val result = listOf(
            mockPrModel,
            mockPrModel
        )

        whenever(dataSource.getPrsForRepository(owner, repo)).thenReturn(Single.just(result))
        val testObserver = TestObserver<List<PrResponseModel>>()

        //when
        val request = dataSource.getPrsForRepository(owner, repo)
        request.subscribeWith(testObserver)

        //then
        testObserver.assertResult(result)
        verify(dataSource).getPrsForRepository(owner, repo)

        testObserver.dispose()
    }

    @Test
    fun `Test get all Prs Error`(){
        //given
        val owner = "test"
        val repo = "repo_test"
        val result = Exception("Error")

        whenever(dataSource.getPrsForRepository(owner, repo)).thenReturn(Single.error(result))
        val testObserver = TestObserver<List<PrResponseModel>>()

        //when
        val request = dataSource.getPrsForRepository(owner, repo)
        request.subscribeWith(testObserver)

        //then
        val errorCount = testObserver.errorCount()
        assertEquals(1, errorCount)
        verify(dataSource).getPrsForRepository(owner, repo)

        testObserver.dispose()
    }

}