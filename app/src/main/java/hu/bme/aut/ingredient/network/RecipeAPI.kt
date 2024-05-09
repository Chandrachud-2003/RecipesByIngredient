package hu.bme.aut.ingredient.network

import hu.bme.aut.ingredient.data.AnalyzedInstruction
import hu.bme.aut.ingredient.data.AnalyzedInstructionItem
import hu.bme.aut.ingredient.data.Recipe
import hu.bme.aut.ingredient.data.RecipeCard
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeAPI {
    // New recipes by ingredients endpoint
    @GET("recipes/findByIngredients")
    suspend fun searchRecipesByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int,
        @Query("limitLicense") limitLicense: Boolean,
        @Query("ranking") ranking: Int,
        @Query("ignorePantry") ignorePantry: Boolean,
        @Query("apiKey") apiKey: String
    ): List<Recipe>

    @GET("recipes/{id}/analyzedInstructions")
    suspend fun getRecipeInstructions(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ) : AnalyzedInstruction

    @GET("recipes/{id}/card")
    suspend fun getRecipeCard(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ) : RecipeCard

}