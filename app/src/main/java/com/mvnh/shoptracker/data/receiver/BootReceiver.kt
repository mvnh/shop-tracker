package com.mvnh.shoptracker.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mvnh.shoptracker.domain.usecase.ProductTrackingServiceUseCase
import com.mvnh.shoptracker.domain.usecase.preference.AutoStartUseCase
import com.mvnh.shoptracker.domain.usecase.preference.StorePartUseCase
import org.koin.java.KoinJavaComponent.inject

class BootReceiver : BroadcastReceiver() {

    private val autoStartUseCase: AutoStartUseCase by inject(AutoStartUseCase::class.java)
    private val productTrackingServiceUseCase: ProductTrackingServiceUseCase by inject(
        ProductTrackingServiceUseCase::class.java
    )
    private val storePartUseCase: StorePartUseCase by inject(StorePartUseCase::class.java)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED && autoStartUseCase.getAutoStartPreference()) {
            productTrackingServiceUseCase.startService(storePartUseCase.getStorePartUid())
        }
    }
}