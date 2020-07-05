package br.com.itau.github

import android.app.Application
import br.com.itau.github.di.repoModule
import br.com.itau.github.di.retrofitModule
import br.com.itau.github.di.schedulerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ItauRepositoriesApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ItauRepositoriesApplication)
            modules(retrofitModule, repoModule, schedulerModule)
        }
    }
}