package com.mvnh.shoptracker.domain.usecase

import android.util.Log
import com.mvnh.shoptracker.domain.model.Product
import com.mvnh.shoptracker.domain.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(storePartUid: String): Result<List<Product>> {
        return try {
            val products = repository.getProducts(storePartUid)
            Log.d(TAG, "Products: $products")
            Result.success(products)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting products", e)
            Result.failure(e)
        }
    }

    companion object {
        private const val TAG = "GetProductsUseCase"
    }
}
