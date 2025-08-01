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
import com.pitrlabs.boringapps.CalculateRaoultQuery
import com.pitrlabs.boringapps.network.ApolloClientInstance
import com.pitrlabs.boringapps.ui.screen.isValidPositiveDecimal
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun RaoultLawScreen(hazeState: HazeState) {
    var purePressure by remember { mutableStateOf("") }
    var soluteFraction by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Double?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val purePressureValue = purePressure.toDoubleOrNull()
    val soluteFractionValue = soluteFraction.toDoubleOrNull()

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
            .padding(top = 40.dp),
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
                        text = "Raoult Law",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.5f),
                                offset = Offset(0f, 2f),
                                blurRadius = 6f
                            )
                        ),
                        color = Color(0xFF68E1FD),
                    )

                    OutlinedTextField(
                        value = purePressure,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                purePressure = it
                            }
                        },
                        label = { Text("pure pressure") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = soluteFraction,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                soluteFraction = it
                            }
                        },
                        label = { Text("solute fraction") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                try {
                                    val response = ApolloClientInstance.client
                                        .query(
                                            CalculateRaoultQuery(
                                                purePressure = purePressureValue ?: 0.0,
                                                soluteFraction = soluteFractionValue ?: 0.0
                                            )
                                        )
                                        .execute()
                                    result = response.data?.calculateRaoult
                                } catch (e: Exception) {
                                    result = null
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.align(Alignment.End),
                        enabled = purePressure.isNotBlank() && soluteFraction.isNotBlank()
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