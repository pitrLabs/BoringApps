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
import com.pitrlabs.boringapps.CalculateVanDerWaalsQuery
import com.pitrlabs.boringapps.network.ApolloClientInstance
import com.pitrlabs.boringapps.ui.screen.isValidPositiveDecimal

@Composable
fun VanDerWaalsScreen() {
    var pressure by remember { mutableStateOf("") }
    var volume by remember { mutableStateOf("") }
    var moles by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var constA by remember { mutableStateOf("") }
    var constB by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Double?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val pressureValue = pressure.toDoubleOrNull()
    val volumeValue = volume.toDoubleOrNull()
    val molesValue = moles.toDoubleOrNull()
    val temperatureValue = temperature.toDoubleOrNull()
    val constAValue = constA.toDoubleOrNull()
    val constBValue = constB.toDoubleOrNull()

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
                        text = "Van Der Waals",
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
                        value = pressure,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                pressure = it
                            }
                        },
                        label = { Text("pressure") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = volume,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                volume = it
                            }
                        },
                        label = { Text("volume") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = moles,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                moles = it
                            }
                        },
                        label = { Text("moles") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = temperature,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                temperature = it
                            }
                        },
                        label = { Text("temperature") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = constA,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                constA = it
                            }
                        },
                        label = { Text("const a") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = constB,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                constB = it
                            }
                        },
                        label = { Text("const b") },
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
                                            CalculateVanDerWaalsQuery(
                                                pressure = pressureValue ?: 0.0,
                                                volume = volumeValue ?: 0.0,
                                                moles = molesValue ?: 0.0,
                                                temperature = temperatureValue ?: 0.0,
                                                constA = constAValue ?: 0.0,
                                                constB = constBValue ?: 0.0
                                            )
                                        )
                                        .execute()
                                    result = response.data?.calculateVanDerWaals
                                } catch (e: Exception) {
                                    result = null
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = pressure.isNotBlank() && volume.isNotBlank() && moles.isNotBlank() && temperature.isNotBlank() && constA.isNotBlank() && constB.isNotBlank()
                    ) {
                        Text("Calculate")
                    }

                    when {
                        isLoading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        }

                        result != null -> {
                            Text(
                                text = "Result: $result",
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