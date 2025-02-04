package com.mvnh.shoptracker.di

import android.app.Application
import androidx.compose.runtime.Composable
import com.mvnh.shoptracker.ui.screen.SettingsScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

val appModule = listOf(
    networkModule,
    repositoryModule,
    useCaseModule,
    storageModule,
    viewModelModule
)

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}

@Composable
fun App() {
    KoinContext {
        SettingsScreen()
    }
}
