package com.mvnh.shoptracker.di

import com.mvnh.shoptracker.domain.usecase.GetProductsUseCase
import com.mvnh.shoptracker.domain.usecase.ProductTrackingServiceUseCase
import com.mvnh.shoptracker.domain.usecase.preference.AutoStartUseCase
import com.mvnh.shoptracker.domain.usecase.preference.PollingIntervalUseCase
import com.mvnh.shoptracker.domain.usecase.preference.StorePartUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { AutoStartUseCase(get()) }
    single { GetProductsUseCase(get()) }
    single { PollingIntervalUseCase(get()) }
    single { ProductTrackingServiceUseCase(get(), get()) }
    single { StorePartUseCase(get()) }
}
