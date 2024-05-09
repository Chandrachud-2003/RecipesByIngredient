package hu.bme.aut.ingredient.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Length(
    @SerialName("number")
    val number: Int,
    @SerialName("unit")
    val unit: String
)