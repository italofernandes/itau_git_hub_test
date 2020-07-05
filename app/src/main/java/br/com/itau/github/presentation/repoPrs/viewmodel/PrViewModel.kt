package br.com.itau.github.presentation.repoPrs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.itau.github.domain.entity.PrEntity
import br.com.itau.github.domain.usecase.pr.RepoPrUseCase

class PrViewModel(
    private val useCase: RepoPrUseCase
): ViewModel() {

    private val _prList: MutableLiveData<List<PrEntity>> by lazy { MutableLiveData<List<PrEntity>>() }
    private val _errorMsgRes: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    private val _repoName: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _emptyList: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>()}
    private val _loading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    val prList: LiveData<List<PrEntity>>
        get() = _prList

    val errorMsgRes: LiveData<Int>
        get() = _errorMsgRes

    val loading: LiveData<Boolean>
        get() = _loading

    val emptyList: LiveData<Boolean>
        get() = _emptyList

    val repoName: LiveData<String>
        get() = _repoName

    fun loadPrs(author: String, repoName: String){
        _loading.value = true
        _repoName.value = repoName

        useCase.getAllPrs(author,
            repoName,{ prs ->
                _loading.value = false

                when(prs.isNotEmpty()) {
                    true -> _prList.value = prs
                    else -> _emptyList.value = true
                }

            },{ error ->
                _loading.value = false
                _errorMsgRes.value = error
            }
        )
    }
}