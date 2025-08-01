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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.apollographql.apollo3.api.Optional
import com.pitrlabs.boringapps.CalculateEquilibriumQuery
import com.pitrlabs.boringapps.model.ConcentrationEntry
import com.pitrlabs.boringapps.network.ApolloClientInstance
import com.pitrlabs.boringapps.type.ConcentrationInput
import com.pitrlabs.boringapps.ui.screen.isValidDecimal
import com.pitrlabs.boringapps.ui.screen.isValidPositiveDecimal
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun EquilibriumScreen(hazeState: HazeState) {
    val reactants = remember {
        mutableStateListOf(
            ConcentrationEntry("", ""),
            ConcentrationEntry("", "")
        )
    }
    val products = remember {
        mutableStateListOf(
            ConcentrationEntry("", ""),
            ConcentrationEntry("", "")
        )
    }

    var result by remember { mutableStateOf<Double?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val glassStyle = HazeStyle(
        backgroundColor = Color.Transparent,
        tints = listOf(
            HazeTint(Color.Transparent)
        ),
        blurRadius = 10.dp
    )

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
                .hazeEffect(state = hazeState, style = glassStyle)
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
                        text = "Equilibrium",
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

                    reactants.indices.forEach { index ->
                        val react = reactants[index]

                        OutlinedTextField(
                            value = react.value,
                            onValueChange = {
                                if (isValidPositiveDecimal(it)) {
                                    reactants[index] = react.copy(value = it)
                                }
                            },
                            label = { Text("Reactants ${index + 1} Value") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = LocalTextStyle.current.copy()
                        )

                        OutlinedTextField(
                            value = react.order,
                            onValueChange = {
                                if (isValidDecimal(it)) {
                                    reactants[index] = react.copy(order = it)
                                }
                            },
                            label = { Text("Reactants ${index + 1} Order") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = LocalTextStyle.current.copy()
                        )
                    }
                    products.indices.forEach { index ->
                        val prod = products[index]

                        OutlinedTextField(
                            value = prod.value,
                            onValueChange = {
                                if (isValidPositiveDecimal(it)) {
                                    products[index] = prod.copy(value = it)
                                }
                            },
                            label = { Text("Products ${index + 1} Value") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = LocalTextStyle.current.copy()
                        )

                        OutlinedTextField(
                            value = prod.order,
                            onValueChange = {
                                if (isValidDecimal(it)) {
                                    products[index] = prod.copy(order = it)
                                }
                            },
                            label = { Text("Products ${index + 1} Order") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = LocalTextStyle.current.copy()
                        )
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                try {
                                    val inputListReactants = reactants.mapNotNull {
                                        val value = it.value.toDoubleOrNull()
                                        val order = it.order.toDoubleOrNull()
                                        if (value != null && order != null) {
                                            ConcentrationInput(
                                                value = Optional.Present(value),
                                                order = Optional.Present(order)
                                            )
                                        } else null
                                    }

                                    val inputListProducts = products.mapNotNull {
                                        val value = it.value.toDoubleOrNull()
                                        val order = it.order.toDoubleOrNull()
                                        if (value != null && order != null) {
                                            ConcentrationInput(
                                                value = Optional.Present(value),
                                                order = Optional.Present(order)
                                            )
                                        } else null
                                    }

                                    val response = ApolloClientInstance.client
                                        .query(
                                            CalculateEquilibriumQuery(
                                                reactants = inputListReactants,
                                                products = inputListProducts
                                            )
                                        )
                                        .execute()
                                    result = response.data?.calculateEquilibrium
                                } catch (e: Exception) {
                                    result = null
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.align(Alignment.End),
                        enabled = reactants.all {it.value.isNotBlank() && it.order.isNotBlank()} && products.all {it.value.isNotBlank() && it.order.isNotBlank()}
                    ) {
                        Text("Calculate")
                    }

                    when {
                        isLoading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        }

                        result != null -> {
                            Text(
                                text = "$result",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    shadow = Shadow(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        offset = Offset(0f, 1f),
                                        blurRadius = 2f
                                    )
                                ),
                                color = Color(0xFF68E1FD)
                            )
                        }
                    }
                }
            }
        }
    }
}