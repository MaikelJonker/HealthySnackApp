package nl.inholland.healthysnackapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import nl.inholland.healthysnackapp.data.UserInfo.SessionManager
import nl.inholland.healthysnackapp.ui.navigation.App
import nl.inholland.healthysnackapp.ui.theme.HealthySnackAppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthySnackAppTheme {
                App(sessionManager)
            }
        }
    }
}
