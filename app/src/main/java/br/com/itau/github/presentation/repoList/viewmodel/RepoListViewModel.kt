package br.com.itau.github.presentation.repoList.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.itau.github.domain.entity.RepoEntity
import br.com.itau.github.domain.usecase.list.RepoListUseCase

class RepoListViewModel(
    private val useCase: RepoListUseCase
): ViewModel() {

    private val _listRepo: MutableLiveData<List<RepoEntity>> by lazy { MutableLiveData<List<RepoEntity>>() }
    private val _errorMsgRes: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    private val _loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    val listRepo: LiveData<List<RepoEntity>>
        get() = _listRepo

    val errorMsgRes: LiveData<Int>
        get() = _errorMsgRes

    val loading: LiveData<Boolean>
        get() = _loading

    private var total: Long = 0
    private var currentPage: Long = 0
    private val cached = arrayListOf<RepoEntity>()

    fun fetchAllRepo(){
        _loading.value = true
        currentPage++
        useCase.getAllRepositories(
            currentPage,
            { repoEntity ->
                _loading.value = false
                total = repoEntity.total

                takeIf { currentPage <= total  }.apply {
                    cached.addAll(repoEntity.repositories)
                    _listRepo.value = cached
                }
            }, { errorRes ->
                _loading.value = false
                _errorMsgRes.value = errorRes
            }
        )
    }

    override fun onCleared() {
        useCase.clearDisposable()
    }
}