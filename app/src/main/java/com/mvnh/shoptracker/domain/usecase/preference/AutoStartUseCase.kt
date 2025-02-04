package com.mvnh.shoptracker.domain.usecase.preference

import android.util.Log
import com.mvnh.shoptracker.domain.repository.AutoStartRepository

class AutoStartUseCase(
    private val repository: AutoStartRepository
) {

    fun getAutoStartPreference(): Boolean {
        val isEnabled = repository.getAutoStartPreference()
        Log.d(TAG, "Auto start preference: $isEnabled")
        return isEnabled
    }

    fun saveAutoStartPreference(isEnabled: Boolean) {
        Log.d(TAG, "Save auto start preference: $isEnabled")
        repository.saveAutoStartPreference(isEnabled)
    }

    companion object {
        private const val TAG = "AutoStartUseCase"
    }
}
