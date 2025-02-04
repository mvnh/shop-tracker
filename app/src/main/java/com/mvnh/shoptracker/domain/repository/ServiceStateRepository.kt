package com.mvnh.shoptracker.domain.repository

fun interface ServiceStateRepository {
    fun getServiceState(): Boolean
}
