package hu.bme.aut.ingredient.ui.screen

import android.util.Log
import android.widget.ScrollView
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import hu.bme.aut.ingredient.data.Ingredient
import hu.bme.aut.ingredient.data.Recipe


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(recipeId: String, navController: NavController, viewModel: MainViewModel) {

    val recipeIdInt = recipeId.toIntOrNull() ?: return
    val recipe = remember { mutableStateOf<Recipe?>(null) }
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }

    LaunchedEffect(Unit) {
        recipe.value = viewModel.getRecipeById(recipeIdInt)
        viewModel.getRecipeCard(recipeIdInt)
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
        content = {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {
                when (val state = viewModel.recipeCardState) {
                    is RecipeCardState.Init -> {}
                    is RecipeCardState.Loading -> CircularProgressIndicator()
                    is RecipeCardState.Success -> {
                        val painter = rememberAsyncImagePainter(state.recipeCard.url)

                        // This will allow the image to be zoomed in and ou
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .pointerInput(Unit) {
                                    detectTransformGestures { _, pan, zoom, _ ->
                                        scale *= zoom

                                        scale = scale.coerceIn(0.5f, 3f)

                                        offset = if (scale == 1f) Offset(0f, 0f) else offset + pan
                                    }
                                }
                                .graphicsLayer(
                                    scaleX = scale, scaleY = scale,
                                    translationX = offset.x, translationY = offset.y
                                )
                        )
                    }

                    is RecipeCardState.Error -> Text("Error: ${state.errorMsg}")
                }
                Column(
                    modifier = Modifier
                        .padding(it)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .padding(8.dp)

                    ) {
                        Text(
                            "Recipe Title: ${recipe.value?.title}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Likes: ${recipe.value?.likes}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .verticalScroll(rememberScrollState())

                    ) {
                        IngredientSection(
                            title = "Used Ingredients",
                            ingredients = recipe.value?.usedIngredients ?: emptyList()
                        )
                        IngredientSection(
                            title = "Missed Ingredients",
                            ingredients = recipe.value?.missedIngredients ?: emptyList()
                        )
                    }
                }

            }

        }
    )
}

@Composable
fun IngredientSection(title: String, ingredients: List<Ingredient>) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium)

        if (ingredients.isEmpty()) {
            Text("No ingredients available", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(8.dp))
        } else {
//            LazyColumn {
//                itemsIndexed(ingredients) { index, ingredient ->
//                    NumberedIngredientItem(index, ingredient)
//                }
//            }

            ingredients.forEachIndexed { index, ingredient ->
                NumberedIngredientItem(index, ingredient)
            }
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

@Composable
fun ZoomableImage(imageUrl: String) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "Zoomable image",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = maxOf(1f, minOf(3f, scale)),
                    scaleY = maxOf(1f, minOf(3f, scale)),
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, rotation ->
                        scale *= zoom
                        offset += pan
                    }
                },
            contentScale = ContentScale.Fit
        )
    }
}