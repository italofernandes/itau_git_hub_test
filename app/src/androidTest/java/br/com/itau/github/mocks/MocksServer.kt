package br.com.itau.github.mocks

import androidx.test.platform.app.InstrumentationRegistry
import br.com.itau.github.data.model.PrResponseModel
import br.com.itau.github.data.model.RepoListResponseModel
import br.com.itau.github.data.repository.GitHubRepository
import br.com.itau.github.domain.entity.RepoEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

class MockServiceRepository: GitHubRepository {
    override fun getAllRepository(page: Long): Single<RepoListResponseModel> {
        return when(page) {
            1L -> Single.just(MocksServer.repoPg1)
            2L -> Single.just(MocksServer.repoPg2)
            else -> Single.error(Exception("list over"))
        }
    }

    override fun getPrsForRepository(
        owner: String,
        repoName: String
    ): Single<List<PrResponseModel>> {
        return Single.just(MocksServer.prMocks)
    }

}

object MocksServer {

    val repoPg1: RepoListResponseModel by lazy {
        Gson().fromJson<RepoListResponseModel>(
            readJsonFile("repo_pg1"),
            RepoListResponseModel::class.java
        )
    }

    val repoPg2: RepoListResponseModel by lazy {
        Gson().fromJson<RepoListResponseModel>(
            readJsonFile("repo_pg2"),
            RepoListResponseModel::class.java
        )
    }

    val prMocks: List<PrResponseModel> by lazy {
        val typeToken = object : TypeToken<List<PrResponseModel>>() {}.type

        Gson().fromJson<List<PrResponseModel>>(
            readJsonFile("pr"),
            typeToken
        )
    }

    @Throws(IOException::class)
    private fun readJsonFile(filename: String): String? {
        val inputStream: InputStream =
            InstrumentationRegistry.getInstrumentation().context
                .assets.open("$filename.json")

        val br = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuilder()
        var line: String? = br.readLine()

        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }

        return sb.toString()
    }

    val mockRepoEntity = RepoEntity(
        name = "Mock Repo",
        description = "This is a mock Repo",
        author = "Mock Author",
        authorImage = "www.test.com/mockImage",
        starsNumber = 30L,
        forkNumber = 345L
    )

}