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
import com.pitrlabs.boringapps.CalculateFreezingPointQuery
import com.pitrlabs.boringapps.network.ApolloClientInstance
import com.pitrlabs.boringapps.ui.screen.isValidPositiveDecimal

@Composable
fun FreezingScreen() {
    var m by remember { mutableStateOf("") }
    var i by remember { mutableStateOf("") }
    var kf by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Double?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val mValue = m.toDoubleOrNull()
    val iValue = i.toDoubleOrNull()
    val kfValue = kf.toDoubleOrNull()

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
                        text = "Freezing Point",
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
                        value = m,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                m = it
                            }
                        },
                        label = { Text("m") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = i,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                i = it
                            }
                        },
                        label = { Text("i") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = kf,
                        onValueChange = {
                            if (isValidPositiveDecimal(it)) {
                                kf = it
                            }
                        },
                        label = { Text("kf") },
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
                                            CalculateFreezingPointQuery(
                                                m = mValue ?: 0.0,
                                                i = iValue ?: 0.0,
                                                kf = kfValue ?: 0.0
                                            )
                                        )
                                        .execute()
                                    result = response.data?.calculateFreezingPoint
                                } catch (e: Exception) {
                                    result = null
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = m.isNotBlank() && i.isNotBlank() && kf.isNotBlank()
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