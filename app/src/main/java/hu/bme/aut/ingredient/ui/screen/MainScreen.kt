package hu.bme.aut.ingredient.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hu.bme.aut.ingredient.data.Recipe
import hu.bme.aut.ingredient.ui.navigation.MainNavigation


@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    var ingredients by rememberSaveable { mutableStateOf("") }

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Ingredients") },
            value = ingredients,
            onValueChange = { ingredients = it },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    mainViewModel.searchRecipesByIngredients(ingredients)
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        )

        when (val state = mainViewModel.recipeUIState) {
            is RecipeUiState.Init -> {}
            is RecipeUiState.Loading -> CircularProgressIndicator()
            is RecipeUiState.Success -> RecipeList(recipes = state.recipes, navController = navController)
            is RecipeUiState.Error -> Text("Error: ${state.errorMsg}")
        }
    }
}

@Composable
fun RecipeList(recipes: List<Recipe>, navController: NavHostController) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(recipes) { recipe ->
            RecipeCard(recipe = recipe, navController = navController )
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(MainNavigation.DetailScreen.createRoute(recipe.id!!))
                       },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.image) // need to add image size
                    .crossfade(true)
                    .build(),
                contentDescription = "Recipe Image",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = recipe.title ?: "No title available",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

//@Composable
//fun MainScreen(
//    mainViewModel: MainViewModel = hiltViewModel()
//) {
//    var earthDate by rememberSaveable { mutableStateOf("2015-6-3") }
//
//    Column {
//        OutlinedTextField(
//            modifier = Modifier.fillMaxWidth(0.8f),
//            label = {
//                Text(text = "Earth date")
//            },
//            value = earthDate,
//            onValueChange = {
//                earthDate = it
//            },
//            singleLine = true,
//            leadingIcon = {
//                Icon(Icons.Default.DateRange, null)
//            }
//        )
//
//        Button(onClick = {
//            mainViewModel.getRoverPhotos(earthDate, "DEMO_KEY")
//        }) {
//            Text(text = "Refresh")
//        }
//
//        when (mainViewModel.marsUIState) {
//            is MarsUiState.Init -> {}
//            is MarsUiState.Loading -> CircularProgressIndicator()
//            is MarsUiState.Success -> ResultScreen(
//                (mainViewModel.marsUIState as MarsUiState.Success).roverPhotos)
//            is MarsUiState.Error -> Text(
//                text = "Error: " +
//                        "${(mainViewModel.marsUIState as MarsUiState.Error).errorMsg}"
//            )
//        }
//    }
//}
//
//@Composable
//fun ResultScreen(roverPhotsResult: RoverPhotos) {
//    LazyColumn(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        items(roverPhotsResult.photos!!) {
//            RoverPhotoCard(roverName = it!!.rover!!.name!!,
//                earthDate = it!!.earthDate!!,
//                photoUrl = it!!.imgSrc!!)
//        }
//    }
//}
//
//
//@Composable
//fun RoverPhotoCard (
//    roverName: String,
//    earthDate: String,
//    photoUrl: String
//) {
//    Card(
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surfaceVariant,
//        ),
//        shape = RoundedCornerShape(20.dp),
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 10.dp
//        ),
//        modifier = Modifier
//            .padding(20.dp)
//            .fillMaxWidth()
//    ) {
//        Column(
//            modifier = Modifier.padding(20.dp)
//        ) {
//
//            Text(
//                text = roverName
//            )
//            Text(
//                text = earthDate
//            )
//
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(photoUrl)
//                    .crossfade(true)
//                    .build(),
//                contentDescription = "Image",
//                contentScale = ContentScale.FillWidth,
//                modifier = Modifier.size(250.dp)
//            )
//        }
//    }
//}