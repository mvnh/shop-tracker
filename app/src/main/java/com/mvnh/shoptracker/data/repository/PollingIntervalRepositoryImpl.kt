package com.mvnh.shoptracker.data.repository

import android.content.SharedPreferences
import com.mvnh.shoptracker.domain.repository.PollingIntervalRepository

class PollingIntervalRepositoryImpl(
    private val preferences: SharedPreferences
) : PollingIntervalRepository {

    override fun savePollingInterval(pollingInterval: Int) {
        preferences.edit().putInt(KEY_POLLING_INTERVAL, pollingInterval).apply()
    }

    override fun getPollingInterval(): Int {
        return preferences.getInt(KEY_POLLING_INTERVAL, 1)
    }

    companion object {
        private const val KEY_POLLING_INTERVAL = "polling_interval"
    }
}
