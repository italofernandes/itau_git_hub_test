package br.com.itau.github.di

import br.com.itau.github.common.domain.usecase.AppSchedulers
import br.com.itau.github.common.domain.usecase.AppSchedulersImpl
import br.com.itau.github.data.datasource.GitHubDataSource
import br.com.itau.github.data.datasource.GitHubWebDataSource
import br.com.itau.github.data.repository.GitHubRepository
import br.com.itau.github.data.repository.GitHubRepositoryImpl
import br.com.itau.github.data.service.GitHubService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single { GsonBuilder().setLenient().create() }
    single<GitHubService> {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        retrofit.create(GitHubService::class.java)
    }
}

val repoModule = module {
    single<GitHubRepository> { GitHubRepositoryImpl(get()) }
}

val useCaseModel = module {
    single<GitHubDataSource> { GitHubWebDataSource(get()) }
}

val schedulerModule = module {
    single<AppSchedulers> {AppSchedulersImpl()}
}



