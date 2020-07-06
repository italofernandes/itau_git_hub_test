package br.com.itau.github

import br.com.itau.github.common.domain.usecase.AppSchedulers
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulers : AppSchedulers {
    override fun main(): Scheduler =  Schedulers.single()
    override fun worker(): Scheduler =  Schedulers.single()
}