package br.com.itau.github.data.datasource

import br.com.itau.github.data.model.PrResponseModel
import br.com.itau.github.data.model.RepoListResponseModel
import br.com.itau.github.data.service.GitHubService
import br.com.itau.github.mockPrModel
import br.com.itau.github.mockRepoResponseModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GitHubDataSourceTest {
    @Mock
    private lateinit var service: GitHubService
    private lateinit var dataSource: GitHubDataSource

    @Before
    fun init(){
        dataSource = GitHubWebDataSource(service)
    }

    @Test
    fun `Test get all repositories`(){
        //given
        val page = 1L
        val result = mockRepoResponseModel

        whenever(service.getRepositories(page)).thenReturn(Observable.just(result))
        val testObserver = TestObserver<RepoListResponseModel>()

        //when
        val request = service.getRepositories(page)
        request.subscribeWith(testObserver)

        //then
        testObserver.assertResult(result)
        verify(service).getRepositories(page)
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

        whenever(service.getPrs(owner, repo)).thenReturn(Observable.just(result))
        val testObserver = TestObserver<List<PrResponseModel>>()

        //when
        val request = service.getPrs(owner, repo)
        request.subscribeWith(testObserver)

        //then
        testObserver.assertResult(result)
        verify(service).getPrs(owner, repo)
        testObserver.dispose()
    }
}