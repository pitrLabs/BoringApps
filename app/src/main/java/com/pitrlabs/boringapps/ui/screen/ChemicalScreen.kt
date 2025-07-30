package com.pitrlabs.boringapps.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pitrlabs.boringapps.ui.component.GlassButton

@Composable
fun ChemicalScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GlassButton(
                onClick = { navController.navigate("periodic_table") },
                text = "Periodic Table",
                modifier = Modifier
                    .weight(1f)
                    .height(70.dp)
            )

            GlassButton(
                onClick = { navController.navigate("compound") },
                text = "Compound",
                modifier = Modifier
                    .weight(1f)
                    .height(70.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        GlassButton(
            onClick = { navController.navigate("ph") },
            text = "pH",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("gas diffusion") },
            text = "Gas Diffusion",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("gibbs") },
            text = "Gibbs Free Energy",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("cell potential") },
            text = "Cell Potential",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("mole fraction") },
            text = "Mole Fraction",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("half life") },
            text = "Half Life",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("rate law") },
            text = "Rate Law",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("enthalpy") },
            text = "Enthalpy Entropy",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("heat capacity") },
            text = "Heat Capacity",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("nernst") },
            text = "Nernst Equation",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("raoult law") },
            text = "Raoult Law",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("henry law") },
            text = "Henry Law",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("buffer ph") },
            text = "Buffer pH",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("titration") },
            text = "Titration Equivalence",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("freezing") },
            text = "Freezing Point",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("boiling") },
            text = "Boiling Point",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("van der waals") },
            text = "Van Der Waals",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("equilibrium") },
            text = "Equilibrium",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("hess law") },
            text = "Hess Law",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlassButton(
            onClick = { navController.navigate("stoichiometry") },
            text = "Stoichiometry",
            modifier = Modifier
                .wrapContentWidth()
                .height(55.dp)
        )
    }
}

fun isValidDecimal(input: String): Boolean {
    val decimalRegex = Regex("^-?\\d*\\.?\\d*$")
    return input.matches(decimalRegex)
}

fun isValidPositiveDecimal(input: String): Boolean {
    val decimalRegex = Regex("^\\d*\\.?\\d*$")
    return input.matches(decimalRegex)
}
