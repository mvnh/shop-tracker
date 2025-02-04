package com.mvnh.shoptracker.data.network.dto

data class ProductDto(
    val uid: Long,
    val title: String,
    val sku: String,
    val quantity: Int,
    val price: Double
)
