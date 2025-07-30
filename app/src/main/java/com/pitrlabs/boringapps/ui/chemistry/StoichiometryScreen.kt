package com.pitrlabs.boringapps.ui.chemistry

import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.apollographql.apollo3.api.Optional
import com.pitrlabs.boringapps.CalculateStoichiometryQuery
import com.pitrlabs.boringapps.model.StoichiometryEntry
import com.pitrlabs.boringapps.network.ApolloClientInstance
import com.pitrlabs.boringapps.type.CompoundInput
import com.pitrlabs.boringapps.type.StoichiometryInput

@Composable
fun StoichiometryScreen() {
    val reactants = remember { mutableStateListOf(StoichiometryEntry("", "")) }
    val products = remember { mutableStateListOf(StoichiometryEntry("", "")) }

    var results by remember {
        mutableStateOf<List<CalculateStoichiometryQuery.CalculateStoichiometry>?>(null)
    }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 35.dp, bottom = 35.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .width(320.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.25f),
                            Color.White.copy(alpha = 0.15f),
                            Color.White.copy(alpha = 0.10f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(400f, 400f)
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.4f),
                            Color.White.copy(alpha = 0.1f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(300f, 300f)
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .padding(24.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .widthIn(min = 300.dp, max = 400.dp)
                ) {
                    Text(
                        text = "Stoichiometry",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.5f),
                                offset = Offset(0f, 2f),
                                blurRadius = 6f
                            )
                        ),
                        color = Color(0xFF68E1FD)
                    )

                    Text("Reactants", fontWeight = FontWeight.Bold)
                    reactants.forEachIndexed { index, entry ->
                        OutlinedTextField(
                            value = entry.formula,
                            onValueChange = { reactants[index] = entry.copy(formula = it) },
                            label = { Text("Formula ${index + 1}") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = entry.coefficient,
                            onValueChange = {
                                if (it.all { ch -> ch.isDigit() }) {
                                    reactants[index] = entry.copy(coefficient = it)
                                }
                            },
                            label = { Text("Coefficient ${index + 1}") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    Button(onClick = { reactants.add(StoichiometryEntry("", "")) }) {
                        Text("+ Add Reactant")
                    }

                    Text("Products", fontWeight = FontWeight.Bold)
                    products.forEachIndexed { index, entry ->
                        OutlinedTextField(
                            value = entry.formula,
                            onValueChange = { products[index] = entry.copy(formula = it) },
                            label = { Text("Formula ${index + 1}") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = entry.coefficient,
                            onValueChange = {
                                if (it.all { ch -> ch.isDigit() }) {
                                    products[index] = entry.copy(coefficient = it)
                                }
                            },
                            label = { Text("Coefficient ${index + 1}") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    Button(onClick = { products.add(StoichiometryEntry("", "")) }) {
                        Text("+ Add Product")
                    }

                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                try {
                                    val reactantInputs = reactants.mapNotNull {
                                        val coeff = it.coefficient.toDoubleOrNull()
                                        if (it.formula.isNotBlank() && coeff != null) {
                                            CompoundInput(
                                                formula = Optional.Present(it.formula),
                                                coefficient = Optional.Present(coeff)
                                            )
                                        } else null
                                    }

                                    val productInputs = products.mapNotNull {
                                        val coeff = it.coefficient.toDoubleOrNull()
                                        if (it.formula.isNotBlank() && coeff != null) {
                                            CompoundInput(
                                                formula = Optional.Present(it.formula),
                                                coefficient = Optional.Present(coeff)
                                            )
                                        } else null
                                    }

                                    val response = ApolloClientInstance.client
                                        .query(
                                            CalculateStoichiometryQuery(
                                                input = StoichiometryInput(
                                                    reactants = Optional.Present(reactantInputs),
                                                    products = Optional.Present(productInputs)
                                                )
                                            )
                                        )
                                        .execute()

                                    results = response.data?.calculateStoichiometry
                                        ?.filterNotNull()
                                } catch (e: Exception) {
                                    results = null
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = reactants.all { it.formula.isNotBlank() && it.coefficient.isNotBlank() } &&
                                products.all { it.formula.isNotBlank() && it.coefficient.isNotBlank() }
                    ) {
                        Text("Calculate")
                    }

                    when {
                        isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        results != null -> {
                            results?.forEach { product ->
                                Text(
                                    text = "Product: ${product.product.orEmpty()}",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF68E1FD)
                                )
                                product.ratios.orEmpty().forEach {
                                    if (it != null)
                                        Text("${it.formula.orEmpty()}: ${it.ratio}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
