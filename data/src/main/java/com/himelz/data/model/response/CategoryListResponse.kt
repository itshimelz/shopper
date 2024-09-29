package com.himelz.data.model.response

import com.himelz.data.model.CategoryDataModel
import com.himelz.domain.model.CategoryListModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoryListResponse(
    val `data`: List<CategoryDataModel>,
    val msg: String
){
    fun toCategoryList(): CategoryListModel {
        return CategoryListModel(
            categories = this.`data`.map { it.toCategory() },
            msg = this.msg
        )

    }
}