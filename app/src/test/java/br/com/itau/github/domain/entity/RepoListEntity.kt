package br.com.itau.github.domain.entity

import br.com.itau.github.mockRepoEntity
import br.com.itau.github.mockRepoListEntity
import junit.framework.Assert.assertEquals
import org.junit.Test

class RepoListEntityTest {

    @Test
    fun `assert repo entity`(){
        val repo = mockRepoEntity

        assertEquals("Mock Repo", repo.name)
        assertEquals("This is a mock Repo", repo.description)
        assertEquals("Mock Author", repo.author)
        assertEquals("www.test.com/mockImage", repo.authorImage)
        assertEquals(30L, repo.starsNumber)
        assertEquals(345L, repo.forkNumber)
    }

    @Test
    fun `assert repo List entity`(){
        val repo = mockRepoListEntity

        assertEquals(3L, repo.total)
        assertEquals(listOf(
            mockRepoEntity,
            mockRepoEntity,
            mockRepoEntity
        ), repo.repositories)

    }
}