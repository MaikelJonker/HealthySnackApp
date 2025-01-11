package nl.inholland.healthysnackapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.inholland.healthysnackapp.ui.home.HomePage
import nl.inholland.healthysnackapp.ui.home.HomeViewModel
import nl.inholland.healthysnackapp.ui.products.ProductList
import nl.inholland.healthysnackapp.ui.products.ProductViewModel
import nl.inholland.healthysnackapp.ui.profile.ProfilePage
import nl.inholland.healthysnackapp.ui.shoppingList.ShoppingListPage

@Composable
fun App() {
    val navController = rememberNavController()
    val productViewModel: ProductViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background ),
        topBar = {
            // TODO top app bar
        },
        bottomBar = {
            NavBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("products") {
                ProductList(productViewModel)
            }

            composable(
                route = "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
            }

            composable("home") {
                HomePage(homeViewModel)
            }

            composable("shoppingList"){
                ShoppingListPage()
            }

            composable("profile") {
                ProfilePage()
            }
        }
    }
}