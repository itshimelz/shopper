package com.himelz.data.repository

import com.himelz.domain.model.Product
import com.himelz.domain.model.ProductListModel
import com.himelz.domain.network.NetworkService
import com.himelz.domain.network.ResultWrapper
import com.himelz.domain.repository.ProductRepository

class ProductRepositoryImpl(private val networkService: NetworkService) : ProductRepository {
    override suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel> {
        return networkService.getProducts(category)
    }
}