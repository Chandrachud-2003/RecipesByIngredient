package hu.bme.aut.ingredient.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    @SerialName("aisle")
    val aisle: String,
    @SerialName("amount")
    val amount: Double,
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("meta")
    val meta: List<String>,
    @SerialName("name")
    val name: String,
    @SerialName("original")
    val original: String,
    @SerialName("originalName")
    val originalName: String,
    @SerialName("unit")
    val unit: String,
    @SerialName("unitLong")
    val unitLong: String,
    @SerialName("unitShort")
    val unitShort: String
)