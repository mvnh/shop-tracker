package com.mvnh.shoptracker.domain.model

data class Product(
    val uid: Long,
    val title: String,
    val sku: String,
    val quantity: Int,
    val price: Double
)
