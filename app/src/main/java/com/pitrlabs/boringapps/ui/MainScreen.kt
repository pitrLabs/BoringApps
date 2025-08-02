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
import com.pitrlabs.boringapps.ui.component.CreditCardSample
import com.pitrlabs.boringapps.ui.screen.WeightPredictionScreen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource

@Composable
fun MainScreen(client: ApolloClient) {
    val navController = rememberNavController()
    val hazeState = remember { HazeState() }
    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().hazeSource(hazeState)
            )

            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                composable("home") { HomeScreen(navController, hazeState) }
                composable("chemical") { ChemicalScreen(navController, hazeState) }
                composable("genai") { DummyScreen("GenAI") }
                composable("vision") { ImageClassificationScreen(hazeState) }
                composable("weight") { WeightPredictionScreen(hazeState) }
                composable("sampling") { CreditCardSample(navController, hazeState) }
                composable("periodic_table") { PeriodicTableScreen(hazeState) }
                composable("compound") { CompoundScreen(hazeState) }
                composable("ph") { PhScreen(hazeState) }
                composable("gas diffusion") { GasDiffusionScreen(hazeState) }
                composable("gibbs") { GibbsScreen(hazeState) }
                composable("cell potential") { CellPotentialScreen(hazeState) }
                composable("mole fraction") { MoleFractionScreen(hazeState) }
                composable("half life") { HalfLifeScreen(hazeState) }
                composable("rate law") { RateLawScreen(hazeState) }
                composable("enthalpy") { EnthalpyScreen(hazeState) }
                composable("heat capacity") { HeatCapacityScreen(hazeState) }
                composable("nernst") { NernstScreen(hazeState) }
                composable("raoult law") { RaoultLawScreen(hazeState) }
                composable("henry law") { HenryLawScreen(hazeState) }
                composable("buffer ph") { BufferPhScreen(hazeState) }
                composable("titration") { TitrationScreen(hazeState) }
                composable("freezing") { FreezingScreen(hazeState) }
                composable("boiling") { BoilingScreen(hazeState) }
                composable("van der waals") { VanDerWaalsScreen(hazeState) }
                composable("equilibrium") { EquilibriumScreen(hazeState) }
                composable("hess law") { HessLawScreen(hazeState) }
                composable("stoichiometry") { StoichiometryScreen(hazeState) }
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
