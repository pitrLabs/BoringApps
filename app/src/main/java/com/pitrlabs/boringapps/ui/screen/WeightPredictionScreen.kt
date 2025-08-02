package com.pitrlabs.boringapps.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pitrlabs.boringapps.ui.WeightViewModel
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun WeightPredictionScreen(hazeState: HazeState) {
    val viewModel: WeightViewModel = viewModel()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.error.collectAsState()
    val predictedWeight by viewModel.weight.collectAsState()

    var heightInput by remember { mutableStateOf("") }
    var genderInput by remember { mutableStateOf("") }

    var showHeightError by remember { mutableStateOf(false) }
    var showGenderError by remember { mutableStateOf(false) }

    val glassStyle = HazeStyle(
        backgroundColor = Color.Transparent,
        tints = listOf(HazeTint(Color.Transparent)),
        blurRadius = 10.dp
    )
    Box(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(320.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(20.dp))
                .hazeEffect(state = hazeState, style = glassStyle)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = heightInput,
                onValueChange = {
                    if (isValidPositiveDecimal(input = it)) {
                        heightInput = it
                    }
                    showHeightError = false
                },
                isError = showHeightError,
                label = { Text("Height (cm)", color = Color(0xFF68E1FD)) },
                supportingText = {
                    if (showHeightError) Text("Fill this", color = Color(0xFFD32F2F))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF68E1FD),
                    unfocusedBorderColor = Color.White.copy(alpha = 0.4f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.8f),
                    cursorColor = Color(0xFF68E1FD),
                    errorBorderColor = Color(0xFFD32F2F),
                    errorTextColor = Color.White,
                    errorCursorColor = Color(0xFFD32F2F)
                ),
                shape = RoundedCornerShape(15.dp)
            )

            var genderExpanded by remember { mutableStateOf(false) }
            val genderOptions = listOf("Male", "Female")

            Box {
                OutlinedTextField(
                    value = genderInput,
                    onValueChange = { },
                    readOnly = true,
                    isError = showGenderError,
                    label = { Text("Gender", color = Color(0xFF68E1FD)) },
                    supportingText = {
                        if (showGenderError) Text("Fill this", color = Color(0xFFD32F2F))
                    },
                    trailingIcon = {
                        IconButton(onClick = { genderExpanded = !genderExpanded }) {
                            Icon(
                                imageVector = if (genderExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Select Gender",
                                tint = Color(0xFF68E1FD)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF68E1FD),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.4f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White.copy(alpha = 0.8f),
                        cursorColor = Color(0xFF68E1FD),
                        errorBorderColor = Color(0xFFD32F2F),
                        errorTextColor = Color.White,
                        errorCursorColor = Color(0xFFD32F2F)
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { genderExpanded = !genderExpanded }
                )

                DropdownMenu(
                    expanded = genderExpanded,
                    onDismissRequest = { genderExpanded = false }

                ) {
                    genderOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (option == "Male") {
                                            Icons.Default.Male
                                        } else {
                                            Icons.Default.Female
                                        },
                                        contentDescription = option,
                                        tint = if (option == "Male") {
                                            Color(0xFFFFB700)
                                        } else {
                                            Color(0xFFE91E63)
                                        },
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = option,
                                        color = if (option == "Male") {
                                            Color(0xFFFFB700)
                                        } else {
                                            Color(0xFFE91E63)
                                        },
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            },
                            onClick = {
                                genderInput = option
                                genderExpanded = false
                                showGenderError = false
                            },
                            modifier = Modifier
                                .hazeEffect(state = hazeState, style = glassStyle)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = if (option == "Male") {
                                            listOf(
                                                Color(0xFF82FF58).copy(alpha = 0.25f),
                                                Color(0xFF82FF58).copy(alpha = 0.15f),
                                                Color(0xFF82FF58).copy(alpha = 0.10f)
                                            )
                                        } else {
                                            listOf(
                                                Color(0xFFF26AFF).copy(alpha = 0.25f),
                                                Color(0xFFF26AFF).copy(alpha = 0.15f),
                                                Color(0xFFF26AFF).copy(alpha = 0.10f)
                                            )
                                        },
                                        start = Offset(0f, 0f),
                                        end = Offset(400f, 400f)
                                    )
                                )
                        )
                    }
                }
            }

            if (!isLoading) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(15.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF68E1FD).copy(alpha = 0.3f),
                                    Color(0xFF68E1FD).copy(alpha = 0.2f),
                                    Color(0xFF68E1FD).copy(alpha = 0.1f)
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(400f, 400f)
                            )
                        )
                        .border(
                            width = 1.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF68E1FD).copy(alpha = 0.5f),
                                    Color(0xFF68E1FD).copy(alpha = 0.2f)
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(300f, 300f)
                            ),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable {
                            val height = heightInput.toIntOrNull()
                            val gender = genderInput.trim()

                            showHeightError = height == null
                            showGenderError = gender.isEmpty()

                            if (height != null && gender.isNotBlank()) {
                                viewModel.predict(height, gender)
                            }
                        }
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Predict Weight",
                        color = Color(0xFF68E1FD),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(15.dp))
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
                                shape = RoundedCornerShape(15.dp)
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color(0xFF68E1FD),
                                strokeWidth = 3.dp
                            )
                            Text(
                                text = "Predicting...",
                                color = Color(0xFF68E1FD),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                errorMessage != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(15.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFD32F2F).copy(alpha = 0.25f),
                                        Color(0xFFD32F2F).copy(alpha = 0.15f),
                                        Color(0xFFD32F2F).copy(alpha = 0.10f)
                                    ),
                                    start = Offset(0f, 0f),
                                    end = Offset(400f, 400f)
                                )
                            )
                            .border(
                                width = 1.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFD32F2F).copy(alpha = 0.4f),
                                        Color(0xFFD32F2F).copy(alpha = 0.1f)
                                    ),
                                    start = Offset(0f, 0f),
                                    end = Offset(300f, 300f)
                                ),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Oops! Something went wrong:\n${errorMessage}",
                            color = Color(0xFFD32F2F),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                predictedWeight != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(15.dp))
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
                                shape = RoundedCornerShape(15.dp)
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Predicted Weight: $predictedWeight kg",
                            color = Color(0xFF68E1FD),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}