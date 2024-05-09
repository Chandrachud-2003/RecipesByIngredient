package hu.bme.aut.ingredient.ui.navigation

import kotlinx.serialization.json.Json

sealed class MainNavigation(val route: String) {
    object MainScreen: MainNavigation("mainscreen")
    object DetailScreen: MainNavigation("detailscreen/{id}") {
        fun createRoute(id: Int): String = "detailscreen/$id"
    }
}