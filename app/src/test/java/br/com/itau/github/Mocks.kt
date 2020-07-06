package br.com.itau.github

import br.com.itau.github.data.model.*
import br.com.itau.github.domain.entity.PrEntity
import br.com.itau.github.domain.entity.RepoEntity
import br.com.itau.github.domain.entity.RepoListEntity

val mockOwnerModel = Owner(
    1L,
    "loginTest",
    "www.test.com/igm"
)

val mockRepoModel = Repository(
    1L,
    "Mock Repo",
    "Mock Repo For test",
    "This is a Mock, for test purpose",
    45L,
    13L,
    mockOwnerModel
)

val mockRepoResponseModel = RepoListResponseModel(
    totalCount = 2L,
    items = listOf(
        mockRepoModel,
        mockRepoModel
    )
)

val userModelMock = User(
    username = "Mock User",
    avatarUrl = "www.test.com/mockimage"
)

val mockPrModel = PrResponseModel(
    number = 35L,
    state = "open",
    title = "This pr is a mock",
    body = "this pr is a mock, ans is used just for test",
    createdDate = "12/03/2020",
    updatedDate = "14/03/2020",
    user = userModelMock
)

val mockPrEntity = PrEntity(
    authorName = "Mock Author",
    authorImage = "www.test.com/mockImage",
    title = "Mock Pr",
    body = "This is a Mock Pr",
    date = "12/02/2020"
)

val mockRepoEntity = RepoEntity(
    name = "Mock Repo",
    description = "This is a mock Repo",
    author = "Mock Author",
    authorImage = "www.test.com/mockImage",
    starsNumber = 30L,
    forkNumber = 345L
)

//val mockRepoModel = Repository(
//    1L,
//    "Mock Repo",
//    "Mock Repo For test",
//    "This is a Mock, for test purpose",
//    45L,
//    13L,
//    mockOwnerModel
//)


val mockRepoEntityUseCase = RepoEntity(
    name = "Mock Repo",
    description = "This is a Mock, for test purpose",
    author = "loginTest",
    authorImage = "www.test.com/igm",
    starsNumber = 13L,
    forkNumber = 45L
)

val mockRepoListEntity = RepoListEntity(
    total = 3L,
    repositories = listOf(
        mockRepoEntity,
        mockRepoEntity,
        mockRepoEntity
    )
)

val mockRepoListEntityUseCase = RepoListEntity(
    total = 2L,
    repositories = listOf(
        mockRepoEntityUseCase,
        mockRepoEntityUseCase
    )
)
