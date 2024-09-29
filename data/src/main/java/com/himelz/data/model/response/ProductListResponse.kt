package com.himelz.data.model.response

import com.himelz.data.model.ProductDataModel
import com.himelz.domain.model.ProductListModel
import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse(
    val `data`: List<ProductDataModel>,
    val msg: String
) {
    fun toProductList(): ProductListModel {
        return ProductListModel(
            products = this.data.map { it.toProduct() },
            msg = this.msg
        )
    }
}