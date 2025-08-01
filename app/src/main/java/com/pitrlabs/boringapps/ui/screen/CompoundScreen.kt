package com.pitrlabs.boringapps.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pitrlabs.boringapps.model.Compound
import com.pitrlabs.boringapps.ui.CompoundViewModel
import com.pitrlabs.boringapps.ui.component.GlassButton
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun CompoundScreen(hazeState: HazeState, viewModel: CompoundViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedCompound by remember { mutableStateOf<Compound?>(null) }

    LaunchedEffect(Unit) {
        Log.d("CompoundViewModel", "loadCompounds called")
        viewModel.loadCompounds()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            uiState.error != null -> {
                Text(
                    text = "Error: ${uiState.error}",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
            uiState.compounds.isEmpty() -> {
                Text(
                    text = "No compounds found.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 128.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.compounds) { compound ->
                        CompoundTitle(hazeState = hazeState, compound = compound) {
                            selectedCompound = it
                        }
                    }
                }

                selectedCompound?.let { compound ->
                    GlassCompoundDialog(
                        hazeState = hazeState,
                        compound = compound,
                        onDismiss = { selectedCompound = null }
                    )
                }
            }
        }
    }
}

@Composable
fun CompoundTitle(hazeState: HazeState, compound: Compound, onClick: (Compound) -> Unit) {
    val formattedFormula = formatChemicalFormulaUnicode(compound.chemicalFormula ?: "")

    GlassButton(
        text = formattedFormula,
        onClick = { onClick(compound) },
        hazeState = hazeState,
        modifier = Modifier.size(64.dp)
    )
}

private fun formatChemicalFormulaUnicode(formula: String): String {
    val subscriptMap = mapOf(
        '0' to '₀', '1' to '₁', '2' to '₂', '3' to '₃', '4' to '₄',
        '5' to '₅', '6' to '₆', '7' to '₇', '8' to '₈', '9' to '₉'
    )

    return formula.map { char ->
        subscriptMap[char] ?: char
    }.joinToString("")
}

@Composable
fun GlassCompoundDialog(
    hazeState: HazeState,
    compound: Compound,
    onDismiss: () -> Unit
) {
    val dialogGlassStyle = HazeStyle(
        backgroundColor = Color.Transparent,
        tint = HazeTint(Color.Transparent),
        blurRadius = 16.dp,
        noiseFactor = 0.15f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable { onDismiss() }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width(320.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(20.dp))
                .hazeEffect(state = hazeState, style = dialogGlassStyle)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { /* Prevent click through */ }
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    compound.chemicalFormula?.let { symbol ->
                        val formattedSymbol = formatChemicalFormulaUnicode(symbol)
                        val dynamicFontSize = when {
                            formattedSymbol.length <= 10 -> 48.sp
                            formattedSymbol.length <= 12 -> 40.sp
                            else -> 30.sp
                        }

                        Text(
                            text = formattedSymbol,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = dynamicFontSize,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.6f),
                                    offset = Offset(0f, 2f),
                                    blurRadius = 4f
                                )
                            ),
                            color = Color(0xFF68E1FD),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    compound.name?.let {
                        Text(
                            text = it,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Medium,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.5f),
                                    offset = Offset(0f, 1f),
                                    blurRadius = 2f
                                )
                            ),
                            color = Color.White
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White.copy(alpha = 0.3f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    CompoundInfoRow(
                        label = "Category",
                        value = compound.category ?: "—"
                    )

                    CompoundInfoRow(
                        label = "Bond Type",
                        value = compound.bondType ?: "—"
                    )

                    CompoundInfoRow(
                        label = "Properties",
                        value = compound.properties ?: "—"
                    )

                    CompoundInfoRow(
                        label = "Uses",
                        value = compound.uses ?: "—"
                    )

                    CompoundInfoRow(
                        label = "Status",
                        value = compound.status ?: "—"
                    )

                    CompoundInfoRow(
                        label = "Discovery Date",
                        value = compound.discoveryDate ?: "—"
                    )

                    CompoundInfoRow(
                        label = "Discovery Period",
                        value = compound.discoveryPeriod ?: "—"
                    )

                    CompoundInfoRow(
                        label = "Discovery By",
                        value = compound.discoveryBy ?: "—"
                    )

                    CompoundInfoRow(
                        label = "Source",
                        value = compound.source ?: "—"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color.White, Color(0xFF68E1FD))
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(1.dp)
                ){
                    GlassButton(
                        text = "Close",
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        hazeState = hazeState,
                    )
                }
            }
        }
    }
}

@Composable
fun CompoundInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = Offset(0f, 1f),
                    blurRadius = 2f
                )
            ),
            color = Color.White.copy(alpha = 0.9f)
        )

        Text(
            text = value.replace(",", ";\n"),
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.bodyMedium.copy(
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
