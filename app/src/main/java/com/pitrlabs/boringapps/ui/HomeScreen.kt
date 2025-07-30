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
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pitrlabs.boringapps.ui.component.GlassButton

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val buttons = listOf(
            "Chemical" to "chemical",
            "GenAI" to "genai",
            "Height Prediction" to "height",
            "Image Classification" to "vision"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GlassButton(
                text = buttons[0].first,
                onClick = { navController.navigate(buttons[0].second) },
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .padding(8.dp)
            )
            GlassButton(
                text = buttons[1].first,
                onClick = { navController.navigate(buttons[1].second) },
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
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .padding(8.dp)
            )
            GlassButton(
                text = buttons[3].first,
                onClick = { navController.navigate(buttons[3].second) },
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .padding(8.dp)
            )
        }
    }
}


