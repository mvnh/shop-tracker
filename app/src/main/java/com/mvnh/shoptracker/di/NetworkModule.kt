package com.mvnh.shoptracker.di

import com.mvnh.shoptracker.data.network.api.ProductApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://127.0.0.1:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(ProductApi::class.java) }
}
