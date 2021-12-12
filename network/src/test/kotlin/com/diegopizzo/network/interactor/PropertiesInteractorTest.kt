package com.diegopizzo.network.interactor

import com.diegopizzo.network.base.Result
import com.diegopizzo.network.creator.PropertiesCreator
import com.diegopizzo.network.model.AveragePrice
import com.diegopizzo.network.model.Properties
import com.diegopizzo.network.model.Property
import com.diegopizzo.network.service.ApiService
import io.reactivex.Single
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PropertiesInteractorTest {

    private lateinit var interactor: IPropertiesInteractor

    @Mock
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        interactor = PropertiesInteractor(apiService, PropertiesCreator())
    }

    @Test
    fun getAveragePropertiesPrice_successResult_assertEqualsTrue() {
        `when`(apiService.getProperties()).thenReturn(Single.just(successResponse))
        val actualValue = interactor.getAveragePropertyPrice().blockingGet()
        assertEquals(expectedAveragePropertiesPrice, actualValue)
    }

    @Test
    fun getAveragePropertiesPrice_networkErrorResult_assertEqualsTrue() {
        `when`(apiService.getProperties()).thenReturn(Single.error(Exception("any error")))
        val actualValue = interactor.getAveragePropertyPrice().blockingGet()
        assertEquals(Result.Error<AveragePrice>(), actualValue)
    }

    @Test
    fun getAveragePropertiesPrice_serverErrorResult_assertEqualsTrue() {
        `when`(apiService.getProperties()).thenReturn(
            Single.just(
                Response.error(
                    500,
                    ResponseBody.create(null, "")
                )
            )
        )
        val actualValue = interactor.getAveragePropertyPrice().blockingGet()
        assertEquals(Result.Error<AveragePrice>(500), actualValue)
    }

    companion object {
        private val successResponse = Response.success(
            Properties(
                listOf(
                    Property(
                        1,
                        1000000,
                        7,
                        2,
                        "12",
                        "Richard Lane",
                        "London",
                        "W1F 3FT",
                        "DETACHED"
                    ),
                    Property(
                        2,
                        100000,
                        2,
                        1,
                        "22",
                        "Brick Road",
                        "Sheffield",
                        "SH1 1AW",
                        "TERRACED"
                    ),
                    Property(
                        3,
                        225000,
                        5,
                        0,
                        "40",
                        "Yellow Lane",
                        "Manchester",
                        "MA12 3ZY",
                        "DETACHED"
                    )
                )
            )
        )
    }

    private val expectedAveragePropertiesPrice = Result.Success(
        AveragePrice("441666.67")
    )
}