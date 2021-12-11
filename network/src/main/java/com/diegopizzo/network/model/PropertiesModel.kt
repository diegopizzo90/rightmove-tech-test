package com.diegopizzo.network.model

/**
 * I don't have any information about the response model in terms of optional/nullable attributes.
 * So I assume that all of it can be null.
 */
internal data class Property(
    val id: Long? = null,
    val price: Int? = null,
    val bedrooms: Int? = null,
    val bathrooms: Int? = null,
    val number: String? = null,
    val address: String? = null,
    val region: String? = null,
    val postcode: String? = null,
    val propertyType: String? = null
)

internal data class Properties(val properties: List<Property>)

@JvmInline
value class AveragePrice(val average: String)