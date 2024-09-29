package com.himelz.data.di

import com.himelz.data.repository.CategoryRepositoryImpl
import com.himelz.data.repository.ProductRepositoryImpl
import com.himelz.domain.repository.CategoryRepository
import com.himelz.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductRepository> {
        ProductRepositoryImpl(get())
    }

    single<CategoryRepository> {
        CategoryRepositoryImpl(get())
    }
}