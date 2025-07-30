package com.pitrlabs.boringapps.model

data class Element(
    val atomicNumber: Int?,
    val atomicMass: Double?,
    val atomicRadius: Double?,
    val covalentRadius: Double?,
    val vanDerWaalsRadius: Double?,
    val symbol: String?,
    val name: String?,
    val category: String?,
    val period: Int?,
    val group: Int?,
    val block: String?,
    val phase: String?,
    val density: Double?,
    val meltingPoint: Double?,
    val boilingPoint: Double?,
    val electronConfiguration: String?,
    val electronegativity: Double?,
    val electronAffinity: Double?,
    val electricalConductivity: Double?,
    val ionizationEnergy: Double?,
    val isotopes: List<Isotope?>?,
    val oxidationState: List<Int?>?,
    val abundanceEarthCrust: Double?,
    val abundanceUniverse: Double?,
    val specificHeat: Double?,
    val appearance: String?,
    val crystalStructure: String?,
    val discoveryYear: Int?,
    val discoveryBy: String?,

)

data class Isotope(
    val symbol: String?,
    val mass: Double?,
    val abundance: Double?,
    val halfLife: String?,
    val decayMode: String?,
)

data class Compound(
    val name: String?,
    val chemicalFormula: String?,
    val category: String?,
    val bondType: String?,
    val properties: String?,
    val uses: String?,
    val status: String?,
    val discoveryDate: String?,
    val discoveryPeriod: String?,
    val discoveryBy: String?,
    val source: String?
)

data class ConcentrationEntry(
    val value: String,
    val order: String
)

data class StoichiometryEntry(
    val formula: String,
    val coefficient: String
)
