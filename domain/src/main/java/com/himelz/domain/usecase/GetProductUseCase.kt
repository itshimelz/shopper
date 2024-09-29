package com.himelz.domain.usecase

import com.himelz.domain.repository.ProductRepository

class GetProductUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(category: Int?) = productRepository.getProducts(category)
}