package com.himelz.domain.di

import com.himelz.domain.usecase.GetCategoryUseCase
import com.himelz.domain.usecase.GetProductUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetProductUseCase(get()) }
    factory { GetCategoryUseCase(get()) }


}