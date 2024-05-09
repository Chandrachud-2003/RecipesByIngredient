package hu.bme.aut.ingredient.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeCard(
    @SerialName("url")
    val url: String
)