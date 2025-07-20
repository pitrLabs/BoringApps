package com.pitrlabs.boringapps.model

data class Element(
    val atomicNumber: Int,
    val name: String,
    val symbol: String,
    val category: String,
    val atomicMass: Double,
    val density: Double,
    val meltingPoint: Double?,
    val boilingPoint: Double?
)
