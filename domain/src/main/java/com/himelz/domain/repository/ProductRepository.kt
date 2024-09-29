package com.himelz.domain.repository

import com.himelz.domain.model.ProductListModel
import com.himelz.domain.network.ResultWrapper

interface ProductRepository {
    suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel>
}