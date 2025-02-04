package com.mvnh.shoptracker.data.repository

import android.content.Context
import android.content.Intent
import com.mvnh.shoptracker.data.service.ProductTrackingService
import com.mvnh.shoptracker.domain.repository.ServiceController

class ProductTrackingServiceController(
    private val context: Context,
) : ServiceController {

    override fun startService(storePartUid: String) {
        val intent = Intent(context, ProductTrackingService::class.java)
        context.startService(intent)
    }

    override fun stopService() {
        val intent = Intent(context, ProductTrackingService::class.java)
        context.stopService(intent)
    }
}
