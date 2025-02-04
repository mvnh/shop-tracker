package com.mvnh.shoptracker.data.repository

import android.content.SharedPreferences
import com.mvnh.shoptracker.domain.repository.AutoStartRepository

class AutoStartRepositoryImpl(
    private val preferences: SharedPreferences
) : AutoStartRepository {

    override fun saveAutoStartPreference(enable: Boolean) {
        preferences.edit().putBoolean(KEY_AUTO_START, enable).apply()
    }

    override fun getAutoStartPreference(): Boolean {
        return preferences.getBoolean(KEY_AUTO_START, false)
    }

    companion object {
        private const val KEY_AUTO_START = "auto_start"
    }
}
