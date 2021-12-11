package com.diegopizzo.network.creator

import com.diegopizzo.network.model.AveragePrice
import com.diegopizzo.network.model.Property
import java.math.RoundingMode

internal class PropertiesCreator {
    fun getAveragePrice(
        properties: List<Property>,
        scale: Int = 2,
        roundingMode: RoundingMode = RoundingMode.HALF_UP
    ): AveragePrice {
        return AveragePrice(
            properties.mapNotNull { it.price }.average()
                .toBigDecimal()
                .setScale(scale, roundingMode).toString()
        )
    }
}