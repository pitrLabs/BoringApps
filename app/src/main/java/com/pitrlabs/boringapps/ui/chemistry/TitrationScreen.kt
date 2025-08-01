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
import com.pitrlabs.boringapps.CalculateTitrationPointQuery
import com.pitrlabs.boringapps.network.ApolloClientInstance
import com.pitrlabs.boringapps.ui.screen.isValidPositiveDecimal
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun TitrationScreen(hazeState: HazeState) {
    var acidVolume by remember { mutableStateOf("") }
    var baseMolarity by remember { mutableStateOf("") }
    var acidMorality by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Double?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val acidVolumeValue = acidVolume.toDoubleOrNull()
    val baseMolarityValue = baseMolarity.toDoubleOrNull()
    val acidMoralityValue = acidMorality.toDoubleOrNull()

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
                        text = "Titration Equivalence",
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
                        value = acidVolume,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                acidVolume = it
                            }
                        },
                        label = { Text("acid volume") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = baseMolarity,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                baseMolarity = it
                            }
                        },
                        label = { Text("base molarity") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = acidMorality,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                acidMorality = it
                            }
                        },
                        label = { Text("acid morality") },
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
                                            CalculateTitrationPointQuery(
                                                acidVolume = acidVolumeValue ?: 0.0,
                                                baseMolarity = baseMolarityValue ?: 0.0,
                                                acidMorality = acidMoralityValue ?: 0.0
                                            )
                                        )
                                        .execute()
                                    result = response.data?.calculateTitrationPoint
                                } catch (e: Exception) {
                                    result = null
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.align(Alignment.End),
                        enabled = acidVolume.isNotBlank() && baseMolarity.isNotBlank() && acidMorality.isNotBlank()
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