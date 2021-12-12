package com.diegopizzo.network.creator

import com.diegopizzo.network.model.AveragePrice
import com.diegopizzo.network.model.Property
import java.math.RoundingMode

internal class PropertiesCreator : IPropertiesCreator {
    override fun getAveragePrice(
        properties: List<Property>,
        scale: Int,
        roundingMode: RoundingMode
    ): AveragePrice {
        return AveragePrice(
            properties.mapNotNull { it.price }.average()
                .toBigDecimal()
                .setScale(scale, roundingMode).toString()
        )
    }
}