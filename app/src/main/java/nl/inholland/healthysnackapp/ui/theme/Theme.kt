package nl.inholland.healthysnackapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF406351), // Dark green
    secondary = Color(0xFFFBFFEE), // Cream
    onPrimary = Color.White, // White for text on buttons
    onSecondary = Color.Black, //Black for on buttons
    surface = Color(0xFF273A31), // Background color for components
    onSurface = Color(0xFF000000), // Black for text
    background = Color(0xFFEFF5F2), // General background
)


@Composable
fun HealthySnackAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color(0xFF406351)
    )
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}