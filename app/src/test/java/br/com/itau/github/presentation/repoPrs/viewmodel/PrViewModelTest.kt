package br.com.itau.github.presentation.repoPrs.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.itau.github.R
import br.com.itau.github.TestSchedulers
import br.com.itau.github.data.model.PrResponseModel
import br.com.itau.github.data.repository.GitHubRepository
import br.com.itau.github.domain.entity.PrEntity
import br.com.itau.github.domain.entity.RepoEntity
import br.com.itau.github.domain.usecase.pr.RepoPrUseCase
import br.com.itau.github.mockPrModel
import br.com.itau.github.mockRepoResponseModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
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
class PrViewModelTest{
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: GitHubRepository

    @Captor
    lateinit var listCaptor: ArgumentCaptor<List<PrEntity>>
    @Captor
    lateinit var errorCaptor: ArgumentCaptor<Int>
    @Captor
    lateinit var loadingCaptor: ArgumentCaptor<Boolean>
    @Captor
    lateinit var emptyCaptor: ArgumentCaptor<Boolean>

    private val lisPrObs: Observer<List<PrEntity>> = mock()
    private val errorMsgObs: Observer<Int> = mock()
    private val loadingObs: Observer<Boolean> = mock()
    private val emptyObs: Observer<Boolean> = mock()

    private lateinit var prUserCase: RepoPrUseCase
    private lateinit var prViewModel: PrViewModel

    @Before
    fun init(){
        prUserCase = RepoPrUseCase(repository, TestSchedulers())
        prViewModel = PrViewModel(prUserCase)

        prViewModel.loading.observeForever(loadingObs)
        prViewModel.errorMsgRes.observeForever(errorMsgObs)
        prViewModel.prList.observeForever(lisPrObs)
        prViewModel.emptyList.observeForever(emptyObs)
    }

    @Test
    fun `test get all prs success`(){
        //given
        val owner = "test owner"
        val repos = "repo"
        val result = listOf(mockPrModel, mockPrModel)

        //when
        whenever(repository.getPrsForRepository(owner, repos)).thenReturn(Single.just(result))

        prViewModel.loadPrs(owner, repos)

        //then
        loadingCaptor.run {
            verify(loadingObs, times(2)).onChanged(capture())
        }

        listCaptor.run {
            verify(lisPrObs, times(1)).onChanged(capture())
            assertEquals(2, this.value.size)
        }
    }

    @Test
    fun `test get all prs error`(){
        //given
        val owner = "test owner"
        val repos = "repo"


        //when
        whenever(repository.getPrsForRepository(owner, repos)).thenReturn(Single.error(Exception("error")))

        prViewModel.loadPrs(owner, repos)

        //then
        loadingCaptor.run {
            verify(loadingObs, times(2)).onChanged(capture())
        }

        errorCaptor.run {
            verify(errorMsgObs, times(1)).onChanged(capture())
            assertEquals(R.string.service_generic_error_msg, this.value)

        }
    }

    @Test
    fun `test get all prs empty`(){
        //given
        val owner = "test owner"
        val repos = "repo"
        val result = listOf<PrResponseModel>()

        //when
        whenever(repository.getPrsForRepository(owner, repos)).thenReturn(Single.just(result))

        prViewModel.loadPrs(owner, repos)

        //then
        loadingCaptor.run {
            verify(loadingObs, times(2)).onChanged(capture())
        }

        emptyCaptor.run {
            verify(emptyObs, times(1)).onChanged(capture())
            assertTrue(this.value)
        }
    }
}