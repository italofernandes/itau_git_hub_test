package br.com.itau.github.data.service

import br.com.itau.github.data.model.PrResponseModel
import br.com.itau.github.data.model.RepoListResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @Headers("Accept: application/vnd.github.mercy-preview+json")
    @GET("https://api.github.com/search/repositories?q=language:Java&sort=stars")
    fun getRepositories(@Query("page") page: Long): Observable<RepoListResponseModel>

    @Headers("Accept: application/vnd.github.mercy-preview+json")
    @GET("repos/{owner}/{repoName}/pulls")
    fun getPrs(@Path("owner") owner: String, @Path("repoName") repoName: String): Observable<List<PrResponseModel>>
}