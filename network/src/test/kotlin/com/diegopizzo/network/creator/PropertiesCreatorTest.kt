package com.diegopizzo.network.creator

import com.diegopizzo.network.model.AveragePrice
import com.diegopizzo.network.model.Property
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PropertiesCreatorTest {

    private lateinit var creator: IPropertiesCreator

    @Before
    fun setUp() {
        creator = PropertiesCreator()
    }

    @Test
    fun getAveragePrice_listOfProperties_assertEqualsTrue() {
        val actualValue = creator.getAveragePrice(properties)
        assertEquals(expectedAveragePrice, actualValue)
    }

    companion object {
        private val properties = listOf(
            Property(
                id = 1,
                price = 1000000
            ),
            Property(
                id = 2,
                price = 100000,
                postcode = "SH1 1AW",
                propertyType = "TERRACED"
            ),
            Property(
                id = 3,
                price = 225000,
                bedrooms = 5
            ),
            Property(
                id = 4,
                bedrooms = 5
            ),
            Property(
                id = 9,
                bedrooms = 5
            ),
            Property(
                id = 9,
                price = 123456,
                bedrooms = 5
            )
        )

        private val expectedAveragePrice = AveragePrice("362114.00")
    }
}