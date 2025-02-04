package com.mvnh.shoptracker.data.repository

import android.content.SharedPreferences
import com.mvnh.shoptracker.domain.repository.StorePartRepository

class StorePartRepositoryImpl(
    private val preferences: SharedPreferences
) : StorePartRepository {

    override fun saveStorePartUid(storePartUid: String) {
        preferences.edit().putString(KEY_STORE_PART_UID, storePartUid).apply()
    }

    override fun getStorePartUid(): String {
        return preferences.getString(KEY_STORE_PART_UID, "") ?: ""
    }

    companion object {
        private const val KEY_STORE_PART_UID = "store_part_uid"
    }
}
