package com.diegopizzo.network.creator

import com.diegopizzo.network.model.AveragePrice
import com.diegopizzo.network.model.Property
import java.math.RoundingMode

internal interface IPropertiesCreator {
    fun getAveragePrice(
        properties: List<Property>,
        scale: Int = 2,
        roundingMode: RoundingMode = RoundingMode.HALF_UP
    ): AveragePrice
}
