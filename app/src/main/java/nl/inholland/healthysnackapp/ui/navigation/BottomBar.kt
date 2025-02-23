package nl.inholland.healthysnackapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val navigationLink: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(navController: NavController){
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            navigationLink = { navController.navigate("home") }
        ),
        BottomNavigationItem(
            title = "Producten",
            selectedIcon = Icons.Filled.Inventory2,
            unselectedIcon = Icons.Outlined.Inventory2,
            navigationLink = { navController.navigate("products") }
        ),
        BottomNavigationItem(
            title = "Boodschappen",
            selectedIcon = Icons.Outlined.Inventory,
            unselectedIcon = Icons.Outlined.Inventory,
            navigationLink = { navController.navigate("shoppingList") }
        ),
        BottomNavigationItem(
            title = "Profiel",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            navigationLink = { navController.navigate("profile") }
        )
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                        item.navigationLink()
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 11.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }
    }
}