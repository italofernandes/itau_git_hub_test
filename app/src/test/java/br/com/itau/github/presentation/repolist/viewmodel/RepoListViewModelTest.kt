package br.com.itau.github.presentation.repolist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.itau.github.TestSchedulers
import br.com.itau.github.data.repository.GitHubRepository
import br.com.itau.github.domain.entity.RepoEntity
import br.com.itau.github.domain.usecase.list.RepoListUseCaseImpl
import br.com.itau.github.mockRepoResponseModel
import br.com.itau.github.presentation.repoList.viewmodel.RepoListViewModel
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class RepoListViewModelTest{

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: GitHubRepository
    @Captor
    lateinit var listCaptor: ArgumentCaptor<List<RepoEntity>>
    @Captor
    lateinit var errorCaptor: ArgumentCaptor<Int>
    @Captor
    lateinit var loadingCaptor: ArgumentCaptor<Boolean>

    private val listRepoObs: Observer<List<RepoEntity>> = mock()
    private val errorMsgObs: Observer<Int> = mock()
    private val loadingObs: Observer<Boolean> = mock()

    private lateinit var viewModel: RepoListViewModel
    private lateinit var useCase: RepoListUseCaseImpl

    @Before
    fun init(){
        useCase = RepoListUseCaseImpl(repository, TestSchedulers())
        viewModel = RepoListViewModel(useCase)

        viewModel.loading.observeForever(loadingObs)
        viewModel.errorMsgRes.observeForever(errorMsgObs)
        viewModel.listRepo.observeForever(listRepoObs)
    }

    @Test
    fun `test get all repositories success`(){
        //given
        val page = 1L
        val result = mockRepoResponseModel

        //when
        whenever(repository.getAllRepository(page)).thenReturn(Single.just(result))

        viewModel.fetchAllRepo()

        //then
        loadingCaptor.run {
            verify(loadingObs, times(2)).onChanged(capture())
        }

        listCaptor.run {
            verify(listRepoObs, times(1)).onChanged(capture())
            assertEquals(2, this.value.size)
        }
    }

    @Test
    fun `test get all repositories error`(){
        //given
        val page = 1L

        //when
        whenever(repository.getAllRepository(page)).thenReturn(Single.error(Exception("error")))

        viewModel.fetchAllRepo()

        //then
        loadingCaptor.run {
            verify(loadingObs, times(2)).onChanged(capture())
        }

        errorCaptor.run {
            verify(errorMsgObs, times(1)).onChanged(capture())
        }
    }
}