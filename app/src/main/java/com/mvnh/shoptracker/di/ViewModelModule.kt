package com.mvnh.shoptracker.di

import com.mvnh.shoptracker.ui.viewmodel.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SettingsViewModel(get(), get(), get(), get()) }
}
