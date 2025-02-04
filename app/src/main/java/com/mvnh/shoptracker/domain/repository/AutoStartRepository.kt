package com.mvnh.shoptracker.domain.repository

interface AutoStartRepository {
    fun saveAutoStartPreference(enable: Boolean)
    fun getAutoStartPreference(): Boolean
}
