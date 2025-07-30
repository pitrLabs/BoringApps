package com.pitrlabs.boringapps.service

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.pitrlabs.boringapps.GetCompoundListQuery
import com.pitrlabs.boringapps.model.Compound
import kotlin.collections.map

class CompoundService(private val apolloClient: ApolloClient) {
    suspend fun getCompoundList(): List<Compound> {
        return try {
            Log.d("CompoundService", "Making GraphQL request...")
            val response: ApolloResponse<GetCompoundListQuery.Data> = apolloClient.query(
                GetCompoundListQuery()
            ).execute()

            Log.d("ElementService", "Response received: ${response.data}")
            Log.d("ElementService", "Errors: ${response.errors}")

            if (response.hasErrors()) {
                Log.e("ElementService", "GraphQL errors: ${response.errors}")
                return emptyList()
            }

            val elements = response.data?.compounds?.data
            if (elements == null) {
                Log.e("ElementService", "Elements data is null")
                return emptyList()
            }

            return elements.map {
                Compound(
                    name = it?.name,
                    chemicalFormula = it?.chemicalFormula,
                    category = it?.category,
                    bondType = it?.bondType,
                    properties = it?.properties,
                    uses = it?.uses,
                    status = it?.status,
                    discoveryDate = it?.discoveryDate,
                    discoveryPeriod = it?.discoveryPeriod,
                    discoveryBy = it?.discoveryBy,
                    source = it?.source
                )
            }

        } catch (e: Exception) {
            Log.e("ElementService", "Failed to fetch element list", e)
            emptyList()
        }
    }
}