package com.pitrlabs.boringapps.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun GlassButton(
    text: String,
    onClick: () -> Unit,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(12.dp)
    val glassStyle = HazeStyle(
        backgroundColor = Color.Transparent,
        tints = listOf(
            HazeTint(Color.Transparent)
        ),
        blurRadius = 10.dp
    )

    Box(
        modifier = modifier
            .height(44.dp)
            .clip(shape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .hazeEffect(state = hazeState, style = glassStyle)
        )

        Text(
            text = text,
            color = Color.White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = Offset(0f, 1f),
                    blurRadius = 2f,

                )
            ),
            modifier = Modifier.align(Alignment.Center).padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}