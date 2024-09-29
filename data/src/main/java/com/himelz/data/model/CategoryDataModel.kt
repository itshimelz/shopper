package com.himelz.data.model

import com.himelz.domain.model.Categories
import kotlinx.serialization.Serializable


@Serializable
data class CategoryDataModel(
    val id: Int,
    val image: String,
    val title: String
) {
    fun toCategory(): Categories {
        return Categories(
            id = this.id,
            image = this.image,
            title = this.title
        )
    }
}