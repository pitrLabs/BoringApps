package com.pitrlabs.boringapps.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.apollographql.apollo3.ApolloClient
import com.pitrlabs.boringapps.R
import com.pitrlabs.boringapps.ui.chemistry.BoilingScreen
import com.pitrlabs.boringapps.ui.chemistry.BufferPhScreen
import com.pitrlabs.boringapps.ui.chemistry.CellPotentialScreen
import com.pitrlabs.boringapps.ui.chemistry.EnthalpyScreen
import com.pitrlabs.boringapps.ui.chemistry.EquilibriumScreen
import com.pitrlabs.boringapps.ui.chemistry.FreezingScreen
import com.pitrlabs.boringapps.ui.chemistry.GasDiffusionScreen
import com.pitrlabs.boringapps.ui.chemistry.HessLawScreen
import com.pitrlabs.boringapps.ui.chemistry.PhScreen
import com.pitrlabs.boringapps.ui.chemistry.GibbsScreen
import com.pitrlabs.boringapps.ui.chemistry.MoleFractionScreen
import com.pitrlabs.boringapps.ui.chemistry.HalfLifeScreen
import com.pitrlabs.boringapps.ui.chemistry.HeatCapacityScreen
import com.pitrlabs.boringapps.ui.chemistry.HenryLawScreen
import com.pitrlabs.boringapps.ui.chemistry.NernstScreen
import com.pitrlabs.boringapps.ui.chemistry.RaoultLawScreen
import com.pitrlabs.boringapps.ui.chemistry.RateLawScreen
import com.pitrlabs.boringapps.ui.chemistry.StoichiometryScreen
import com.pitrlabs.boringapps.ui.chemistry.TitrationScreen
import com.pitrlabs.boringapps.ui.chemistry.VanDerWaalsScreen
import com.pitrlabs.boringapps.ui.screen.ChemicalScreen
import com.pitrlabs.boringapps.ui.screen.PeriodicTableScreen
import com.pitrlabs.boringapps.ui.screen.CompoundScreen
import com.pitrlabs.boringapps.ui.screen.ImageClassificationScreen

@Composable
fun MainScreen(client: ApolloClient) {
    val navController = rememberNavController()

    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                composable("home") { HomeScreen(navController) }
                composable("chemical") { ChemicalScreen(navController) }
                composable("genai") { DummyScreen("GenAI") }
                composable("vision") { ImageClassificationScreen() }
                composable("height") { DummyScreen("Height Prediction") }
                composable("periodic_table") { PeriodicTableScreen() }
                composable("compound") { CompoundScreen() }
                composable("ph") { PhScreen() }
                composable("gas diffusion") { GasDiffusionScreen() }
                composable("gibbs") { GibbsScreen() }
                composable("cell potential") { CellPotentialScreen() }
                composable("mole fraction") { MoleFractionScreen() }
                composable("half life") { HalfLifeScreen() }
                composable("rate law") { RateLawScreen() }
                composable("enthalpy") { EnthalpyScreen() }
                composable("heat capacity") { HeatCapacityScreen() }
                composable("nernst") { NernstScreen() }
                composable("raoult law") { RaoultLawScreen() }
                composable("henry law") { HenryLawScreen() }
                composable("buffer ph") { BufferPhScreen() }
                composable("titration") { TitrationScreen() }
                composable("freezing") { FreezingScreen() }
                composable("boiling") { BoilingScreen() }
                composable("van der waals") { VanDerWaalsScreen() }
                composable("equilibrium") { EquilibriumScreen() }
                composable("hess law") { HessLawScreen() }
                composable("stoichiometry") { StoichiometryScreen() }
            }
        }
    }
}

@Composable
fun DummyScreen(title: String) {
    Surface {
        Text(text = "$title - Coming Soon")
    }
}
