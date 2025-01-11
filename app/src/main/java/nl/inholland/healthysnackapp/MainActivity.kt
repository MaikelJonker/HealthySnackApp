package nl.inholland.healthysnackapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import nl.inholland.healthysnackapp.ui.navigation.App
import nl.inholland.healthysnackapp.ui.theme.HealthySnackAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthySnackAppTheme {
                App()
            }
        }
    }
}
