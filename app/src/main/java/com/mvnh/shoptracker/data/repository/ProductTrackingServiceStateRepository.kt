package com.mvnh.shoptracker.data.repository

import android.app.ActivityManager
import android.content.Context
import com.mvnh.shoptracker.data.service.ProductTrackingService
import com.mvnh.shoptracker.domain.repository.ServiceStateRepository

class ProductTrackingServiceStateRepository(
    private val context: Context
) : ServiceStateRepository {

    override fun getServiceState(): Boolean {
        val activityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        @Suppress("DEPRECATION")
        return activityManager.getRunningServices(Int.MAX_VALUE).any {
            it.service.className == ProductTrackingService::class.java.name
        }
    }
}
