package com.mvnh.shoptracker.data.network.api

import com.mvnh.shoptracker.data.network.dto.GetProductsResponse
import retrofit2.Response

interface ProductApi {

    suspend fun getProducts(): Response<GetProductsResponse> = TODO("Удалено в публичном репозитории")
}
