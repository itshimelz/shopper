package com.himelz.data.model

import com.himelz.domain.model.Product
import kotlinx.serialization.Serializable


@Serializable
class ProductDataModel(
    private val categoryId: Int,
    private val description: String,
    private val id: Int,
    private val image: String,
    private val price: Double,
    private val title: String
) {

    fun toProduct(): Product {
        return Product(
            categoryId = this.categoryId,
            description = this.description,
            id = this.id,
            image = this.image,
            price = this.price,
            title = this.title
        )
    }

}