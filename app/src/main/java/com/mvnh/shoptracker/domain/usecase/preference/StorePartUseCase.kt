package com.mvnh.shoptracker.domain.usecase.preference

import android.util.Log
import com.mvnh.shoptracker.domain.repository.StorePartRepository

class StorePartUseCase(
    private val repository: StorePartRepository
) {
    fun getStorePartUid(): String {
        val uid = repository.getStorePartUid()
        Log.d(TAG, "storePartUid: $uid")
        return uid
    }

    fun saveStorePartUid(storePartUid: String) {
        Log.d(TAG, "Saving storePartUid: $storePartUid")
        repository.saveStorePartUid(storePartUid)
    }

    companion object {
        private const val TAG = "StorePartUseCase"
    }
}
