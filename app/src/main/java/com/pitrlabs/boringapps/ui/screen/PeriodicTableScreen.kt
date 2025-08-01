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
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pitrlabs.boringapps.model.Element
import com.pitrlabs.boringapps.ui.ElementViewModel
import com.pitrlabs.boringapps.ui.component.GlassButton
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun PeriodicTableScreen( hazeState: HazeState, viewModel: ElementViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedElement by remember { mutableStateOf<Element?>(null) }

    LaunchedEffect(Unit) {
        Log.d("ElementViewModel", "loadElements called")
        viewModel.loadElements()
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
            uiState.elements.isEmpty() -> {
                Text(
                    text = "No elements found.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 64.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.elements) { element ->
                        ElementTile(element = element, hazeState = hazeState) {
                            selectedElement = it
                        }
                    }
                }

                selectedElement?.let { element ->
                    GlassElementDialog(
                        element = element,
                        hazeState = hazeState,
                        onDismiss = { selectedElement = null }
                    )
                }
            }
        }
    }
}

@Composable
fun ElementTile(element: Element, hazeState: HazeState, onClick: (Element) -> Unit) {
    GlassButton(
        text = element.symbol ?: "",
        onClick = { onClick(element) },
        modifier = Modifier.size(64.dp),
        hazeState = hazeState,
    )
}

@Composable
fun GlassElementDialog(
    hazeState: HazeState,
    element: Element,
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
                    element.symbol?.let { symbol ->
                        Text(
                            text = symbol,
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 48.sp,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.6f),
                                    offset = Offset(0f, 2f),
                                    blurRadius = 4f
                                )
                            ),
                            color = Color(0xFF68E1FD)
                        )
                    }

                    element.name?.let {
                        Text(
                            text = it,
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
                    ElementInfoRow(
                        label = "Atomic Number",
                        value = element.atomicNumber?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Atomic Mass",
                        value = element.atomicMass?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Atomic Radius",
                        value = element.atomicRadius?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Covalent Radius",
                        value = element.covalentRadius?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Van Der Waals Radius",
                        value = element.vanDerWaalsRadius?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Category",
                        value = element.category ?: "—"
                    )

                    ElementInfoRow(
                        label = "Period",
                        value = element.period?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Block",
                        value = element.block ?: "—"
                    )

                    ElementInfoRow(
                        label = "Phase",
                        value = element.phase ?: "—"
                    )

                    ElementInfoRow(
                        label = "Density",
                        value = element.density?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Melting Point",
                        value = element.meltingPoint?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Boiling Point",
                        value = element.boilingPoint?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Electron Configuration",
                        value = element.electronConfiguration ?: "—"
                    )

                    ElementInfoRow(
                        label = "Electron Affinity",
                        value = element.electronAffinity?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Electronegativity",
                        value = element.electronegativity?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Electrical Conductivity",
                        value = element.electricalConductivity?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Ionization Energy",
                        value = element.ionizationEnergy?.toString() ?: "—"
                    )

                    ElementInfoMultiline(
                        label = "Isotopes",
                        value = element.isotopes
                            ?.filterNotNull()
                            ?.joinToString("\n") { isotope ->
                                buildString {
                                    append("${isotope.symbol ?: "-"}\n")
                                    isotope.mass?.let { append("\tmass $it\n") }
                                    isotope.abundance?.let { append("\tabundance $it%\n") }
                                    if (!isotope.halfLife.isNullOrBlank()) append("\thalf-life ${isotope.halfLife}\n")
                                    if (!isotope.decayMode.isNullOrBlank()) append("\tdecay ${isotope.decayMode}\n")
                                }.trimEnd()
                            } ?: "-"
                    )

                    ElementInfoRow(
                        label = "Oxidation State",
                        value = element.oxidationState?.filterNotNull()?.joinToString(", ") { it.toString() } ?: "No data"
                    )

                    ElementInfoRow(
                        label = "Abundance Earth Crust",
                        value = element.abundanceEarthCrust?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Abundance Universe",
                        element.abundanceUniverse?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Specific Heat",
                        value = element.specificHeat?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Appearance",
                        value = element.appearance ?: "—"
                    )

                    ElementInfoRow(
                        label = "Crystal Structure",
                        value = element.crystalStructure ?: "—"
                    )

                    ElementInfoRow(
                        label = "Discovery Year",
                        value = element.discoveryYear?.toString() ?: "—"
                    )

                    ElementInfoRow(
                        label = "Discoverer",
                        value = element.discoveryBy ?: "—"
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
fun ElementInfoRow(label: String, value: String) {
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
            text = value.replace(",", "\n"),
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

@Composable
fun ElementInfoMultiline(label: String, value: String) {
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
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = Offset(0f, 1f),
                    blurRadius = 2f
                )
            ),
            color = Color(0xFF68E1FD),
            lineHeight = 20.sp
        )
    }
}
