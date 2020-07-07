package br.com.itau.github.tools

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import br.com.itau.github.mocks.TestApplication

class KoinRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(
            cl, TestApplication::class.java.name, context
        )
    }
}