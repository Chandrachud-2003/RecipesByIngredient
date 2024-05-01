package hu.bme.aut.ingredient.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoverPhotos(
    @SerialName("photos")
    var photos: List<Photo>? = listOf()
)