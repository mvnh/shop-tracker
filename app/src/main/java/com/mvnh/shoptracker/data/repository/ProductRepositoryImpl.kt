package com.mvnh.shoptracker.data.repository

import com.mvnh.shoptracker.data.network.api.ProductApi
import com.mvnh.shoptracker.domain.model.Product
import com.mvnh.shoptracker.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val api: ProductApi
) : ProductRepository {

    override suspend fun getProducts(storePartUid: String): List<Product> =
        TODO("Удалено в публичном репозитории")
}
