package br.com.itau.github.common.domain.usecase

import androidx.annotation.VisibleForTesting
import br.com.itau.github.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.net.SocketTimeoutException

abstract class BaseUseCase {
    @VisibleForTesting
    private val disposableBag = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) = disposableBag.add(disposable)
    fun clearDisposable() = disposableBag.clear()

    protected fun handleException(exception: Throwable): Int =
        if (exception is SocketTimeoutException) {
            R.string.service_conn_error_msg
        } else {
            R.string.service_generic_error_msg
        }
}