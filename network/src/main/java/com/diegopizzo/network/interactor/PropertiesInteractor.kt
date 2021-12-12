package com.diegopizzo.network.interactor

import com.diegopizzo.network.base.BaseInteractor
import com.diegopizzo.network.base.Result
import com.diegopizzo.network.creator.IPropertiesCreator
import com.diegopizzo.network.model.AveragePrice
import com.diegopizzo.network.service.ApiService
import io.reactivex.Single
import java.util.concurrent.TimeUnit

internal class PropertiesInteractor(
    private val apiService: ApiService,
    private val creator: IPropertiesCreator
) : BaseInteractor(), IPropertiesInteractor {

    override fun getAveragePropertyPrice(): Single<Result<AveragePrice>> {
        return apiService.getProperties().map {
            when (val result = handleResponse(it)) {
                is Result.Success -> Result.Success(creator.getAveragePrice(result.data.properties))
                is Result.Error -> Result.Error(result.errorCode)
            }
        }.onErrorReturn {
            Result.Error()
        }.delay(1L, TimeUnit.SECONDS) //Only added to simulate the wait time during a network request and show the progress bar in the UI layer
    }
}