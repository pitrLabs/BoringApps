package com.pitrlabs.boringapps.service

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.pitrlabs.boringapps.GetElementListQuery
import com.pitrlabs.boringapps.model.Element
import com.pitrlabs.boringapps.model.Isotope

class ElementService(private val apolloClient: ApolloClient) {
    suspend fun getElementList(): List<Element> {
        return try {
            Log.d("ElementService", "Making GraphQL request...")
            val response: ApolloResponse<GetElementListQuery.Data> = apolloClient.query(
                GetElementListQuery()
            ).execute()

            Log.d("ElementService", "Response received: ${response.data}")
            Log.d("ElementService", "Errors: ${response.errors}")

            if (response.hasErrors()) {
                Log.e("ElementService", "GraphQL errors: ${response.errors}")
                return emptyList()
            }

            val elements = response.data?.elements?.data
            if (elements == null) {
                Log.e("ElementService", "Elements data is null")
                return emptyList()
            }

            return elements.map {
                Element(
                    atomicNumber = it?.atomicNumber,
                    atomicMass = it?.atomicMass,
                    atomicRadius = it?.atomicRadius,
                    covalentRadius = it?.covalentRadius,
                    vanDerWaalsRadius = it?.vanDerWaalsRadius,
                    name = it?.name,
                    symbol = it?.symbol,
                    category = it?.category,
                    period = it?.period,
                    group = it?.group,
                    block = it?.block,
                    phase = it?.phase,
                    density = it?.density,
                    meltingPoint = it?.meltingPoint,
                    boilingPoint = it?.boilingPoint,
                    electronConfiguration = it?.electronConfiguration,
                    electronegativity = it?.electronegativity,
                    electronAffinity = it?.electronAffinity,
                    electricalConductivity = it?.electricalConductivity,
                    ionizationEnergy = it?.ionizationEnergy,
                    isotopes = it?.isotopes?.map { iso ->
                        iso?.let {
                            Isotope(
                                symbol = it.symbol,
                                mass = it.mass,
                                abundance = it.abundance,
                                halfLife = it.halfLife,
                                decayMode = it.decayMode
                            )
                        }
                    },
                    oxidationState = it?.oxidationState,
                    abundanceEarthCrust = it?.abundanceEarthCrust,
                    abundanceUniverse = it?.abundanceUniverse,
                    specificHeat = it?.specificHeat,
                    appearance = it?.appearance,
                    crystalStructure = it?.crystalStructure,
                    discoveryYear = it?.discoveryYear,
                    discoveryBy = it?.discoveryBy
                )
            }

        } catch (e: Exception) {
            Log.e("ElementService", "Failed to fetch element list", e)
            emptyList()
        }
    }
}