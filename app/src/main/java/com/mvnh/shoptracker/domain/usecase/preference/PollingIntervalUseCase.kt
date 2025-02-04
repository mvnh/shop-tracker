package com.mvnh.shoptracker.domain.usecase.preference

import android.util.Log
import com.mvnh.shoptracker.domain.repository.PollingIntervalRepository

class PollingIntervalUseCase(
    private val repository: PollingIntervalRepository
) {

    fun getPollingInterval(): Int {
        val pollingInterval = repository.getPollingInterval()
        Log.d(TAG, "Polling interval: $pollingInterval ms")
        return pollingInterval
    }

    fun savePollingInterval(pollingInterval: Int) {
        Log.d(TAG, "Save polling interval: $pollingInterval ms")
        repository.savePollingInterval(pollingInterval)
    }

    companion object {
        private const val TAG = "PollingIntervalUseCase"
    }
}
