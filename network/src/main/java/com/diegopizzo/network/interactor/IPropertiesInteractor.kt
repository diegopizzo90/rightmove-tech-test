package com.diegopizzo.network.interactor

import com.diegopizzo.network.base.Result
import com.diegopizzo.network.model.AveragePrice
import io.reactivex.Single

interface IPropertiesInteractor {
    fun getAveragePropertyPrice(): Single<Result<AveragePrice>>
}
