package com.mvnh.shoptracker.domain.repository

import com.mvnh.shoptracker.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(storePartUid: String): List<Product>
}
