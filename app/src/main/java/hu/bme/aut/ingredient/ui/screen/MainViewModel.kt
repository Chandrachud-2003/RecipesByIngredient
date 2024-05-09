package hu.bme.aut.ingredient.ui.screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.ingredient.data.Recipe
import hu.bme.aut.ingredient.network.RecipeAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val recipesAPI: RecipeAPI
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(listOf())

    var recipeUIState: RecipeUiState by mutableStateOf(RecipeUiState.Init)

     fun getRecipeById(id: Int): Recipe? {
        // Use first to safely access the current list of recipes
         Log.d("RECIPES IN VIEW MODEL", _recipes.value.size.toString())

        return _recipes.value.firstOrNull { recipe ->
            recipe.id == id
        }
    }

    fun searchRecipesByIngredients(ingredients: String, apiKey: String = "5d27038a017540f88b8bf04f28a5a08b") {
        recipeUIState = RecipeUiState.Loading

        viewModelScope.launch {
            try {
                val result = recipesAPI.searchRecipesByIngredients(ingredients = ingredients, number = 5, limitLicense = true, ranking = 1, ignorePantry = true, apiKey = apiKey)
                _recipes.value = result
                Log.d("RECIPES AFTER API", _recipes.value[0].title!!)
                recipeUIState = RecipeUiState.Success(result)
            } catch (e: Exception) {
                recipeUIState = RecipeUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

}

sealed interface RecipeUiState {
    object Init : RecipeUiState
    object Loading : RecipeUiState
    data class Success(val recipes: List<Recipe>) : RecipeUiState
    data class Error(val errorMsg: String) : RecipeUiState
}


//@HiltViewModel
//class MainViewModel @Inject constructor(
//    var marsAPI: MarsAPI
//) : ViewModel() {
//
//    var marsUIState: MarsUiState by mutableStateOf(MarsUiState.Init)
//
//    fun getRoverPhotos(date: String, apiKey: String) {
//        marsUIState = MarsUiState.Loading
//
//        viewModelScope.launch {
//            try {
//                val result = marsAPI.getRoverPhotos(date, apiKey)
//
//                marsUIState = MarsUiState.Success(result)
//            } catch (e: Exception) {
//                marsUIState = MarsUiState.Error(e.message!!)
//            }
//        }
//    }
//}
//
//sealed interface MarsUiState {
//    object Init : MarsUiState
//    object Loading : MarsUiState
//    data class Success(val roverPhotos: RoverPhotos) : MarsUiState
//    data class Error(val errorMsg: String) : MarsUiState
//}

