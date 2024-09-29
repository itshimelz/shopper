package com.himelz.shopper.navigation


import kotlinx.serialization.Serializable


sealed class Destinations {
    @Serializable
    data object HomeScreen : Destinations()

    @Serializable
    data object DetailsScreen : Destinations()

    @Serializable
    data object CartScreen : Destinations()

    @Serializable
    data object ProfileScreen : Destinations()

    @Serializable
    data class ProductDetailsScreen(val productId: Int) : Destinations()
}
