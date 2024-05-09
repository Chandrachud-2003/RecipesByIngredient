package hu.bme.aut.ingredient.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientX(
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("localizedName")
    val localizedName: String,
    @SerialName("name")
    val name: String
)