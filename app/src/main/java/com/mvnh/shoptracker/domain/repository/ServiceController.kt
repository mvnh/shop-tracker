package com.mvnh.shoptracker.domain.repository

interface ServiceController {
    fun startService(storePartUid: String)
    fun stopService()
}
