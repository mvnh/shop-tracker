package com.mvnh.shoptracker.domain.repository

interface StorePartRepository {
    fun saveStorePartUid(storePartUid: String)
    fun getStorePartUid(): String
}
