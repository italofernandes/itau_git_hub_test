package br.com.itau.github.common.domain.usecase

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


interface AppSchedulers {
    fun main(): Scheduler
    fun worker(): Scheduler
}

class AppSchedulersImpl: AppSchedulers {
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
    override fun worker(): Scheduler = Schedulers.io()
}