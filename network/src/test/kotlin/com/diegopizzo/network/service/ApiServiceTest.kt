package com.diegopizzo.network.service

import com.diegopizzo.network.config.NetworkConstant.PROPERTIES_ENDPOINT
import com.diegopizzo.network.model.Properties
import com.diegopizzo.network.model.Property
import com.diegopizzo.network.testutil.enqueueResponse
import com.google.gson.Gson
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ApiServiceTest {
    private lateinit var server: MockWebServer

    private lateinit var api: ApiService

    @Before
    fun setUp() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url(BASE_URL))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun after() {
        server.close()
    }

    private fun successResponse(fileName: String = "properties_success_response.json"): Response<Properties> {
        server.enqueueResponse(fileName, 200)
        return api.getProperties().blockingGet()
    }

    @Test
    fun getProperties_request_assertEqualsTrue() {
        successResponse()

        val request = server.takeRequest()
        assertEquals(request.path, BASE_URL + PROPERTIES_ENDPOINT)
    }

    @Test
    fun getProperties_successResponse_assertEqualsTrue() {
        val response = successResponse()
        assertEquals(expectedSuccessResponse.body(), response.body())
    }

    @Test
    fun getProperties_successIncompleteResponse_assertEqualsTrue() {
        val response = successResponse("properties_incomplete_success_response.json")
        assertEquals(expectedSuccessIncompleteResponse.body(), response.body())
    }

    @Test
    fun getProperties_serverErrorResponse_assertEqualsTrue() {
        server.enqueueResponse("properties_success_response.json", 500)
        val response = api.getProperties().blockingGet()
        assertEquals(null, response.body())
    }

    @Test
    fun getProperties_clientErrorResponse_assertEqualsTrue() {
        server.enqueueResponse("properties_success_response.json", 400)
        val response = api.getProperties().blockingGet()
        assertEquals(null, response.body())
    }

    companion object {
        private const val BASE_URL = "/"

        private val expectedSuccessResponse = Response.success(
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

        private val expectedSuccessIncompleteResponse = Response.success(
            Properties(
                listOf(
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
                    )
                )
            )
        )
    }
}