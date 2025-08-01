package com.pitrlabs.boringapps.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pitrlabs.boringapps.ui.component.GlassButton
import dev.chrisbanes.haze.HazeState

@Composable
fun ChemicalScreen(navController: NavHostController, hazeState: HazeState) {
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
                hazeState = hazeState,
                modifier = Modifier
                    .weight(1f)
                    .height(70.dp)
            )

            GlassButton(
                onClick = { navController.navigate("compound") },
                text = "Compound",
                hazeState = hazeState,
                modifier = Modifier
                    .weight(1f)
                    .height(70.dp)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Thermodynamics",
                modifier = Modifier
                    .align(Alignment.Start)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 10.dp,
                            bottomEnd = 10.dp
                        )
                    )
                    .background(Color(0xFF68E1FD))
                    .padding(horizontal = 4.dp),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GlassButton(
                    onClick = { navController.navigate("gibbs") },
                    text = "Gibbs Free Energy",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("enthalpy") },
                    text = "Enthalpy Entropy",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("hess law") },
                    text = "Hess Law",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("heat capacity") },
                    text = "Heat Capacity",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("van der waals") },
                    text = "Van Der Waals",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Solutions & Colligative Properties",
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topEnd = 10.dp,
                            bottomEnd = 10.dp
                        )
                    )
                    .background(Color(0xFF68E1FD))
                    .padding(horizontal = 4.dp),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GlassButton(
                    onClick = { navController.navigate("raoult law") },
                    text = "Raoult Law",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("henry law") },
                    text = "Henry Law",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("freezing") },
                    text = "Freezing Point",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("boiling") },
                    text = "Boiling Point",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("mole fraction") },
                    text = "Mole Fraction",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Electrochemistry",
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topEnd = 10.dp,
                            bottomEnd = 10.dp
                        )
                    )
                    .background(Color(0xFF68E1FD))
                    .padding(horizontal = 4.dp),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GlassButton(
                    onClick = { navController.navigate("cell potential") },
                    text = "Cell Potential",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("nernst") },
                    text = "Nernst Equation",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Chemical Kinetics",
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topEnd = 10.dp,
                            bottomEnd = 10.dp
                        )
                    )
                    .background(Color(0xFF68E1FD))
                    .padding(horizontal = 4.dp),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GlassButton(
                    onClick = { navController.navigate("rate law") },
                    text = "Rate Law",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("half life") },
                    text = "Half Life",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Equilibrium & Acid-base Chemistry",
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topEnd = 10.dp,
                            bottomEnd = 10.dp
                        )
                    )
                    .background(Color(0xFF68E1FD))
                    .padding(horizontal = 4.dp),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GlassButton(
                    onClick = { navController.navigate("equilibrium") },
                    text = "Equilibrium",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("ph") },
                    text = "pH",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("buffer ph") },
                    text = "Buffer pH",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("titration") },
                    text = "Titration Equivalence",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Stoichiometry & Transport",
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topEnd = 10.dp,
                            bottomEnd = 10.dp
                        )
                    )
                    .background(Color(0xFF68E1FD))
                    .padding(horizontal = 4.dp),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GlassButton(
                    onClick = { navController.navigate("gas diffusion") },
                    text = "Gas Diffusion",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )

                GlassButton(
                    onClick = { navController.navigate("stoichiometry") },
                    text = "Stoichiometry",
                    hazeState = hazeState,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(55.dp)
                )
            }
            }
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
