package com.mvnh.shoptracker.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.mvnh.shoptracker.R
import com.mvnh.shoptracker.domain.model.Product
import com.mvnh.shoptracker.domain.usecase.preference.PollingIntervalUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class ProductTrackingService : Service() {

//    private val getProductsUseCase: GetProductsUseCase by inject()
    private val pollingIntervalUseCase: PollingIntervalUseCase by inject()

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private var previousProducts: List<Product> = emptyList()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createServiceNotification()
        startForeground(1, notification)

        scope.launch {
            startProductPolling()
        }

        return START_STICKY
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Product Tracking Service Channel",
            NotificationManager.IMPORTANCE_HIGH
        )

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun createServiceNotification(): Notification {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.tracking_mosmetroshop))
            .setContentText("...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setSilent(true)

        return builder.build()
    }

    private suspend fun startProductPolling() {
        withContext(Dispatchers.IO) {
            while (true) {
                Log.d(TAG, "Удалено в публичном репозитории")

                delay(pollingIntervalUseCase.getPollingInterval().toLong())
            }
        }
    }

    private fun processNewProducts(products: List<Product>) {
        if (previousProducts.isEmpty()) {
            previousProducts = products
        }

        val newProducts = products.filter { currentProduct ->
            previousProducts.none { it.uid == currentProduct.uid }
            true
        }
        Log.d(TAG, "New products: $newProducts")

        if (newProducts.isNotEmpty()) {
            notifyNewProducts(newProducts)
            previousProducts = products
        }
    }

    private fun notifyNewProducts(products: List<Product>) {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val manager = getSystemService(NotificationManager::class.java)

        products.forEach { product ->
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(product.title)
                .setContentText(getString(R.string.number_of_pieces_left, product.quantity.toString()))
                .setGroup(GROUP_KEY_PRODUCTS)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            manager.notify(product.uid.hashCode(), notification)
        }

        val summaryNotification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.new_product_available))
            .setContentText(getString(R.string.new_items, products.size.toString()))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(Notification.DEFAULT_ALL)
            .setGroup(GROUP_KEY_PRODUCTS)
            .setGroupSummary(true)
            .build()

        manager.notify(0, summaryNotification)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val TAG = "ProductTrackingService"
        private const val CHANNEL_ID = "ProductTrackingServiceChannel"
        private const val GROUP_KEY_PRODUCTS = "com.mvnh.shoptracker.PRODUCTS"
    }
}
