package com.mvnh.shoptracker.domain.state

sealed class ServiceState {
    data object Idle : ServiceState()
    data object Running : ServiceState()
    data class Stopped(val message: String?) : ServiceState()
}
