package com.himelz.data.repository

import com.himelz.domain.model.CategoryListModel
import com.himelz.domain.network.NetworkService
import com.himelz.domain.network.ResultWrapper
import com.himelz.domain.repository.CategoryRepository

class CategoryRepositoryImpl(private val networkService: NetworkService) : CategoryRepository {
    override suspend fun getCategories(): ResultWrapper<CategoryListModel> {
        return networkService.getCategories()
    }
}