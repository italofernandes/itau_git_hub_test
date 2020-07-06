package br.com.itau.github.data.model

import br.com.itau.github.mockPrModel
import br.com.itau.github.userModelMock
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test

class PrtResponseModelTest {

    @Test
    fun `assert user model`(){
        val user = userModelMock

        assertNotNull(user)
        assertEquals("Mock User", user.username)
        assertEquals("www.test.com/mockimage", user.avatarUrl)
    }

    @Test
    fun `assert pr model`(){
        val pr = mockPrModel

        assertNotNull(pr)
        assertEquals(35L, pr.number)
        assertEquals("open", pr.state)
        assertEquals("This pr is a mock", pr.title)
        assertEquals("this pr is a mock, ans is used just for test", pr.body)
        assertEquals("12/03/2020", pr.createdDate)
        assertEquals("14/03/2020", pr.updatedDate)
        assertEquals(userModelMock, pr.user)
    }
}