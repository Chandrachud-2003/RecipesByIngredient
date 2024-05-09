package hu.bme.aut.ingredient.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import hu.bme.aut.ingredient.data.Ingredient
import hu.bme.aut.ingredient.data.Recipe


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(recipeId: String, navController: NavController, viewModel: MainViewModel) {

    val recipeIdInt = recipeId.toIntOrNull() ?: return
    val recipe = remember { mutableStateOf<Recipe?>(null) }

    LaunchedEffect(Unit) {
        recipe.value = viewModel.getRecipeById(recipeIdInt)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe: ${recipe.value?.title}") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Image(
                    painter = rememberImagePainter(recipe.value?.image),
                    contentDescription = "Recipe Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column (
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Recipe Title: ${recipe.value?.title}", style = MaterialTheme.typography.titleMedium)
                    Text("Likes: ${recipe.value?.likes}", style = MaterialTheme.typography.bodyMedium)
                    Text("Used ingredients: ${recipe.value?.usedIngredientCount}", style = MaterialTheme.typography.bodyMedium)
                }
                IngredientSection(title = "Used Ingredients", ingredients = recipe.value?.usedIngredients ?: emptyList())
                IngredientSection(title = "Missed Ingredients", ingredients = recipe.value?.missedIngredients ?: emptyList())
            }
        }
    )
}

@Composable
fun IngredientSection(title: String, ingredients: List<Ingredient>) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium)

        if (ingredients.isEmpty()) {
            Text("No ingredients available", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
        } else {
            LazyColumn {
                itemsIndexed(ingredients) { index, ingredient ->
                    NumberedIngredientItem(index, ingredient)
                }
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: Ingredient) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(ingredient.name, style = MaterialTheme.typography.titleMedium)
            Text("Amount: ${ingredient.amount} ${ingredient.unit}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun NumberedIngredientItem(index: Int, ingredient: Ingredient) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "${index + 1}. ${ingredient.name} (${ingredient.amount} ${ingredient.unit})",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}