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
import nl.inholland.healthysnackapp.ui.login.LoginPage
import nl.inholland.healthysnackapp.ui.productDetail.ProductDetailPage
import nl.inholland.healthysnackapp.ui.products.ProductList
import nl.inholland.healthysnackapp.ui.profile.ProfilePage
import nl.inholland.healthysnackapp.ui.recipeDetail.RecipeDetailPage
import nl.inholland.healthysnackapp.ui.recipeDetail.RecipeRating
import nl.inholland.healthysnackapp.ui.recipeDetail.RecipeStep
import nl.inholland.healthysnackapp.ui.shoppingList.ShoppingListPage

@Composable
fun App() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background ),
        bottomBar = {
            if (currentRoute != "login") { // Hide BottomBar on LoginPage
                NavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("products") {
                ProductList(
                    toProductDetail = { barcode -> navController.navigate("products/$barcode") },
                    viewModel = hiltViewModel()
                )
            }
            composable(
                route = "products/{barcode}",
                arguments = listOf(navArgument("barcode") { type = NavType.StringType })
            ) {backStackEntry ->
                val id = backStackEntry.arguments?.getString("barcode") ?:0
                ProductDetailPage(
                    barcode = id.toString(),
                    onBackClick = { navController.popBackStack() },
                    viewModel = hiltViewModel()
                )
            }
            composable(
                route = "recipes/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                RecipeDetailPage(
                    recipeId = id,
                    viewModel = hiltViewModel(),
                    onBackClick = { navController.popBackStack() },
                    onStartRecipeClick = { id, step -> navController.navigate("recipes/$id/$step") }
                )
            }
            composable(
                route = "recipes/{id}/{step}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("step") { type = NavType.IntType }
                )
            ){ backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                val step = backStackEntry.arguments?.getInt("step") ?: 1

                RecipeStep(
                    viewModel = hiltViewModel(),
                    recipeId = id,
                    recipeStep = step,
                    onBackClick = { navController.popBackStack() },
                    onNextClick = { id, step -> navController.navigate("recipes/$id/$step") },
                    onRatingsClick = { id -> navController.navigate("recipes/$id/rating") }
                )
            }
            composable(
                route = "recipes/{id}/rating",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                )
            ){ backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0

                RecipeRating(
                    viewModel = hiltViewModel(),
                    recipeId = id
                )
            }
            composable("home") {
                HomePage(
                    viewModel = hiltViewModel(),
                    toDetail = { id -> navController.navigate("recipes/$id") },
                    toLogin = { navController.navigate("login") }
                )
            }
            composable("shoppingList"){
                ShoppingListPage(
                    viewModel = hiltViewModel()
                )
            }
            composable("profile") {
                ProfilePage(
                    userId = 1,
                    viewModel = hiltViewModel()
                )
            }
            composable("login") {
                LoginPage(
                    viewModel = hiltViewModel(),
                    onLoginSuccess = { id -> navController.navigate("home") } //TODO: go to home with userId
                )
            }
        }
    }
}