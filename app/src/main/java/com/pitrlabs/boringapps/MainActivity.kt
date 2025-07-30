package com.pitrlabs.boringapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pitrlabs.boringapps.network.ApolloClientInstance
import com.pitrlabs.boringapps.ui.MainScreen
import com.pitrlabs.boringapps.ui.theme.BoringAppsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoringAppsTheme {
                MainScreen(ApolloClientInstance.client)
            }
        }
    }
}
