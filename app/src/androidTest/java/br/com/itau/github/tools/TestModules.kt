package br.com.itau.github.tools

import br.com.itau.github.common.domain.usecase.AppSchedulers
import br.com.itau.github.data.repository.GitHubRepository
import br.com.itau.github.data.repository.GitHubRepositoryImpl
import br.com.itau.github.data.service.GitHubService
import br.com.itau.github.mocks.MockServiceRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val testSchedulerModule = module {
    single<AppSchedulers> { TestSchedulers() }
}

val repoTestModule = module {
    single<GitHubRepository> { MockServiceRepository() }
}