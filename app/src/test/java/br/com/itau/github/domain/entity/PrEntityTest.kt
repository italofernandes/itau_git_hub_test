package br.com.itau.github.domain.entity

import br.com.itau.github.mockPrEntity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test

class PrEntityTest {
    @Test
    fun `assert PR Entity`(){
        val prEntity = mockPrEntity

        assertNotNull(prEntity)
        assertEquals("Mock Author", prEntity.authorName)
        assertEquals("www.test.com/mockImage", prEntity.authorImage)
        assertEquals("Mock Pr", prEntity.title)
        assertEquals("This is a Mock Pr", prEntity.body)
        assertEquals("12/02/2020", prEntity.date)
    }
}