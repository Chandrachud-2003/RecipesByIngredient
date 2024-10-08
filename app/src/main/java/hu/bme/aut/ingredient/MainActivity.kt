package hu.bme.aut.ingredient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.ingredient.ui.navigation.MainNavigation
import hu.bme.aut.ingredient.ui.screen.DetailScreen
import hu.bme.aut.ingredient.ui.screen.MainScreen
import hu.bme.aut.ingredient.ui.screen.MainViewModel
import hu.bme.aut.ingredient.ui.theme.IngredientTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IngredientTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeAppNavHost()
                }
            }
        }
    }
}

@Composable
fun RecipeAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainNavigation.MainScreen.route,
    viewModel: MainViewModel = hiltViewModel()
) {

    NavHost(
        modifier=modifier, navController=navController, startDestination=startDestination
    ){
        composable(MainNavigation.MainScreen.route) {
            MainScreen(navController = navController, mainViewModel = viewModel)
        }

        composable(
            MainNavigation.DetailScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            // Decode the recipe JSON string or handle it accordingly
            DetailScreen(recipeId = backStackEntry.arguments?.getString("id")!!, navController = navController, viewModel)
        }
    }
}