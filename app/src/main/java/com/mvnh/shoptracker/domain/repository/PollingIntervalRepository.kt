package com.mvnh.shoptracker.domain.repository

interface PollingIntervalRepository {
    fun savePollingInterval(pollingInterval: Int)
    fun getPollingInterval(): Int
}
