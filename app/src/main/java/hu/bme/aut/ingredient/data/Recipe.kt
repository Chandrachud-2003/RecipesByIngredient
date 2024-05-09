package hu.bme.aut.ingredient.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    @SerialName("id")
    var id: Int? = 0,
    @SerialName("image")
    var image: String? = null,
    @SerialName("imageType")
    var imageType: String? = null,
    @SerialName("likes")
    var likes: Int? = 0,
    @SerialName("missedIngredientCount")
    var missedIngredientCount: Int? = 0,
    @SerialName("title")
    var title: String? = null,
    @SerialName("usedIngredientCount")
    var usedIngredientCount: Int? = 0,
    @SerialName("missedIngredients")
    var missedIngredients: List<Ingredient>? = emptyList(),
    @SerialName("usedIngredients")
    var usedIngredients: List<Ingredient>? = emptyList()

)
