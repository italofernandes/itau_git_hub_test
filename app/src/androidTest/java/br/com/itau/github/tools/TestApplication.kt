package br.com.itau.github.mocks

import android.app.Application
import br.com.itau.github.tools.repoTestModule
import br.com.itau.github.tools.testSchedulerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TestApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TestApplication)
            modules(repoTestModule, testSchedulerModule)
        }
    }
}