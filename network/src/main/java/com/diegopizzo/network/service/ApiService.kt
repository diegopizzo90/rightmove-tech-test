package com.diegopizzo.network.service

import com.diegopizzo.network.config.NetworkConstant.PROPERTIES_ENDPOINT
import com.diegopizzo.network.model.Properties
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

internal interface ApiService {
    @GET(PROPERTIES_ENDPOINT)
    fun getProperties(): Single<Response<Properties>>
}