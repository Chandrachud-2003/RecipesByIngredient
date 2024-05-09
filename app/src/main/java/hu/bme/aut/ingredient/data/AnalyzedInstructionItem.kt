package hu.bme.aut.ingredient.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnalyzedInstructionItem(
    @SerialName("name")
    val name: String,
    @SerialName("steps")
    val steps: List<Step>
)