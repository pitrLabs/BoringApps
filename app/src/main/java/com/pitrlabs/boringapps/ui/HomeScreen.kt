package com.pitrlabs.boringapps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pitrlabs.boringapps.ui.component.GlassButton
import dev.chrisbanes.haze.HazeState

@Composable
fun HomeScreen(navController: NavController, hazeState: HazeState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val buttons = listOf(
            "Chemistry" to "chemical",
            "Simple Generative" to "genai",
            "Simple Human Weight Prediction" to "weight",
            "Simple Image Classification" to "vision"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GlassButton(
                text = buttons[0].first,
                onClick = { navController.navigate(buttons[0].second) },
                hazeState = hazeState,
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .padding(8.dp)
            )
            GlassButton(
                text = buttons[1].first,
                onClick = { navController.navigate(buttons[1].second) },
                hazeState = hazeState,
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .padding(8.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GlassButton(
                text = buttons[2].first,
                onClick = { navController.navigate(buttons[2].second) },
                hazeState = hazeState,
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .padding(8.dp)
            )
            GlassButton(
                text = buttons[3].first,
                onClick = { navController.navigate(buttons[3].second) },
                hazeState = hazeState,
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .padding(8.dp)
            )
        }
    }
}


