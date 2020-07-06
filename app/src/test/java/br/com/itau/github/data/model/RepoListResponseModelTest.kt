package br.com.itau.github.data.model

import br.com.itau.github.mockOwnerModel
import br.com.itau.github.mockRepoModel
import br.com.itau.github.mockRepoResponseModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test

class RepoListResponseModelTest {

    @Test
    fun `assert owner`(){
        val owner = mockOwnerModel

        assertNotNull(owner)
        assertEquals(1L, owner.id)
        assertEquals("loginTest", owner.username)
        assertEquals("www.test.com/igm", owner.userImgUrl)
    }

    @Test
    fun `assert Respository`(){
        val repo = mockRepoModel

        assertNotNull(repo)
        assertEquals(1L, repo.id)
        assertEquals("Mock Repo", repo.nameRepo)
        assertEquals("Mock Repo For test", repo.fullNameRepo)
        assertEquals("This is a Mock, for test purpose", repo.description)
        assertEquals(45L, repo.forksNumbers)
        assertEquals(13L, repo.starsNumber)
        assertEquals(mockOwnerModel, repo.owner)

    }

    @Test
    fun `assert Respository Response`(){
        val responseModel = mockRepoResponseModel

        assertNotNull(responseModel)
        assertEquals(2L, responseModel.totalCount)
        assertEquals(listOf(
            mockRepoModel,
            mockRepoModel
        ), responseModel.items)
    }
}
