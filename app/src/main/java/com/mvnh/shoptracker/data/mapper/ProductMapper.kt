package com.mvnh.shoptracker.data.mapper

import com.mvnh.shoptracker.data.network.dto.ProductDto
import com.mvnh.shoptracker.domain.model.Product

fun ProductDto.toModel() = Product(
    uid = this.uid,
    title = this.title,
    sku = this.sku,
    quantity = this.quantity,
    price = this.price
)
