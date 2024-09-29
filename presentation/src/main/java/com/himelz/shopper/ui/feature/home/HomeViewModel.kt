package com.himelz.shopper.ui.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himelz.domain.model.Product
import com.himelz.domain.network.ResultWrapper
import com.himelz.domain.usecase.GetCategoryUseCase
import com.himelz.domain.usecase.GetProductUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUIEvents>(HomeScreenUIEvents.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchHomeScreenData()
    }

    private fun fetchHomeScreenData() {
        viewModelScope.launch {
            _uiState.value = HomeScreenUIEvents.Loading

            // Fetch categories and products concurrently
            val categoriesDeferred = async { getCategories() }
            val featuredProductsDeferred = async { getProducts(1) }
            val popularProductsDeferred = async { getProducts(2) }

            val categories = categoriesDeferred.await()
            val featuredProducts = featuredProductsDeferred.await()
            val popularProducts = popularProductsDeferred.await()

            if (categories.isEmpty() || featuredProducts.isEmpty() || popularProducts.isEmpty()) {
                _uiState.value = HomeScreenUIEvents.Error("Failed to load data")
                return@launch
            }

            _uiState.value =
                HomeScreenUIEvents.Success(categories, featuredProducts, popularProducts)
        }
    }

    private suspend fun getProducts(category: Int?): List<Product> {
        return when (val result = getProductUseCase.invoke(category)) {
            is ResultWrapper.Success -> result.value.products
            is ResultWrapper.Failure -> {
                Log.e("ViewModel", "Failed to get products: $result")
                emptyList()
            }
        }
    }

    private suspend fun getCategories(): List<String> {
        return when (val result = getCategoryUseCase.invoke()) {
            is ResultWrapper.Success -> result.value.categories.map { it.title }
            is ResultWrapper.Failure -> {
                Log.e("ViewModel", "Failed to get categories: $result")
                emptyList()
            }
        }
    }
}

sealed class HomeScreenUIEvents {
    data object Loading : HomeScreenUIEvents()
    data class Success(
        val categories: List<String>,
        val featuredProducts: List<Product>,
        val popularProducts: List<Product>
    ) : HomeScreenUIEvents()

    data class Error(val message: String) : HomeScreenUIEvents()
}
