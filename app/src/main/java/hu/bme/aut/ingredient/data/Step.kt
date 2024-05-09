package hu.bme.aut.ingredient.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Step(
    @SerialName("equipment")
    val equipment: List<Equipment>,
    @SerialName("ingredients")
    val ingredients: List<IngredientX>,
    @SerialName("length")
    val length: Length,
    @SerialName("number")
    val number: Int,
    @SerialName("step")
    val step: String
)