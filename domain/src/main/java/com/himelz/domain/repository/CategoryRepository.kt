package com.himelz.domain.repository

import com.himelz.domain.model.CategoryListModel
import com.himelz.domain.network.ResultWrapper

interface CategoryRepository {
    suspend fun getCategories(): ResultWrapper<CategoryListModel>
}