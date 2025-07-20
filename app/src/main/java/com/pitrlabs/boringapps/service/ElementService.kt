package com.pitrlabs.boringapps.service

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.pitrlabs.boringapps.GetElementListMutation
import com.pitrlabs.boringapps.model.Element
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class ElementService(private val apolloClient: ApolloClient) {

    suspend fun getElementList(): List<Element> {
        return try {
            Log.d("ElementService", "Making GraphQL request...")
            val response: ApolloResponse<GetElementListMutation.Data> = apolloClient.mutation(GetElementListMutation()).execute()

            Log.d("ElementService", "Response received: ${response.data}")
            Log.d("ElementService", "Errors: ${response.errors}")

            if (response.hasErrors()) {
                Log.e("ElementService", "GraphQL errors: ${response.errors}")
                return emptyList()
            }

            val result = response.data?.getElementList
            Log.d("ElementService", "GetElementList result: $result")

            if (result?.data == null) {
                Log.w("ElementService", "No data in response")
                return emptyList()
            }

            val dataString = result.data
            Log.d("ElementService", "Raw data string: $dataString")

            return parseElementsFromJson(dataString)

        } catch (e: Exception) {
            Log.e("ElementService", "Failed to fetch element list", e)
            emptyList()
        }
    }

    private fun parseElementsFromJson(jsonString: String): List<Element> {
        return try {
            val json = Json { ignoreUnknownKeys = true }
            val jsonElement = json.parseToJsonElement(jsonString)

            val elements = mutableListOf<Element>()

            when {
                jsonElement.jsonArray.isNotEmpty() -> {
                    // It's an array of elements
                    jsonElement.jsonArray.forEach { elementJson ->
                        val element = parseElement(elementJson.jsonObject)
                        element?.let { elements.add(it) }
                    }
                }
                else -> {
                    val element = parseElement(jsonElement.jsonObject)
                    element?.let { elements.add(it) }
                }
            }

            elements
        } catch (e: Exception) {
            Log.e("ElementService", "Failed to parse JSON: $jsonString", e)
            emptyList()
        }
    }

    private fun parseElement(jsonObject: kotlinx.serialization.json.JsonObject): Element? {
        return try {
            Element(
                atomicNumber = jsonObject["atomic_number"]?.jsonPrimitive?.content?.toIntOrNull() ?: 0,
                name = jsonObject["name"]?.jsonPrimitive?.content ?: "",
                symbol = jsonObject["symbol"]?.jsonPrimitive?.content ?: "",
                category = jsonObject["category"]?.jsonPrimitive?.content ?: "",
                atomicMass = jsonObject["atomic_mass"]?.jsonPrimitive?.content?.toDoubleOrNull() ?: 0.0,
                density = jsonObject["density"]?.jsonPrimitive?.content?.toDoubleOrNull() ?: 0.0,
                meltingPoint = jsonObject["melting_point"]?.jsonPrimitive?.content?.toDoubleOrNull(),
                boilingPoint = jsonObject["boiling_point"]?.jsonPrimitive?.content?.toDoubleOrNull()
            )
        } catch (e: Exception) {
            Log.e("ElementService", "Failed to parse element: $jsonObject", e)
            null
        }
    }
}