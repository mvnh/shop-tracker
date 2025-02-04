package com.mvnh.shoptracker.di

import com.mvnh.shoptracker.data.repository.AutoStartRepositoryImpl
import com.mvnh.shoptracker.data.repository.PollingIntervalRepositoryImpl
import com.mvnh.shoptracker.data.repository.ProductRepositoryImpl
import com.mvnh.shoptracker.data.repository.ProductTrackingServiceController
import com.mvnh.shoptracker.data.repository.ProductTrackingServiceStateRepository
import com.mvnh.shoptracker.data.repository.StorePartRepositoryImpl
import com.mvnh.shoptracker.domain.repository.AutoStartRepository
import com.mvnh.shoptracker.domain.repository.PollingIntervalRepository
import com.mvnh.shoptracker.domain.repository.ProductRepository
import com.mvnh.shoptracker.domain.repository.ServiceController
import com.mvnh.shoptracker.domain.repository.ServiceStateRepository
import com.mvnh.shoptracker.domain.repository.StorePartRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AutoStartRepository> { AutoStartRepositoryImpl(get()) }
    single<PollingIntervalRepository> { PollingIntervalRepositoryImpl(get()) }
    single<StorePartRepository> { StorePartRepositoryImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<ServiceController> { ProductTrackingServiceController(get()) }
    single<ServiceStateRepository> { ProductTrackingServiceStateRepository(get()) }
}
