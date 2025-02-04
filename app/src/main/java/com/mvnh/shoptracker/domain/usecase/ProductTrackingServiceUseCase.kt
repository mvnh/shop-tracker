package com.mvnh.shoptracker.domain.usecase

import android.util.Log
import com.mvnh.shoptracker.domain.repository.ServiceController
import com.mvnh.shoptracker.domain.repository.ServiceStateRepository
import com.mvnh.shoptracker.domain.state.ServiceState

class ProductTrackingServiceUseCase(
    private val controller: ServiceController,
    private val stateRepository: ServiceStateRepository
) {

    fun startService(storePartUid: String): Result<ServiceState> {
        return try {
            controller.startService(storePartUid)
            Log.d(TAG, "Service started")
            Result.success(ServiceState.Running)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start service", e)
            Result.failure(e)
        }
    }

    fun stopService(): Result<ServiceState> {
        return try {
            controller.stopService()
            Log.d(TAG, "Service stopped")
            Result.success(ServiceState.Stopped("Service stopped"))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to stop service", e)
            Result.failure(e)
        }
    }

    fun getServiceState(): Result<ServiceState> {
        when (stateRepository.getServiceState()) {
            true -> {
                Log.d(TAG, "Service is running")
                return Result.success(ServiceState.Running)
            }
            false -> {
                Log.d(TAG, "Service is stopped")
                return Result.success(ServiceState.Stopped("Service stopped"))
            }
        }
    }

    companion object {
        private const val TAG = "ServiceUseCase"
    }
}
