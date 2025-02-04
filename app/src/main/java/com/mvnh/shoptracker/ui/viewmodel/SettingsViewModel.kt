package com.mvnh.shoptracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvnh.shoptracker.domain.state.ServiceState
import com.mvnh.shoptracker.domain.usecase.ProductTrackingServiceUseCase
import com.mvnh.shoptracker.domain.usecase.preference.AutoStartUseCase
import com.mvnh.shoptracker.domain.usecase.preference.PollingIntervalUseCase
import com.mvnh.shoptracker.domain.usecase.preference.StorePartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val autoStartUseCase: AutoStartUseCase,
    private val pollingIntervalUseCase: PollingIntervalUseCase,
    private val productTrackingServiceUseCase: ProductTrackingServiceUseCase,
    private val storePartUseCase: StorePartUseCase
) : ViewModel() {

    private val _autoStart = MutableStateFlow(false)
    val autoStart: StateFlow<Boolean> get() = _autoStart

    private val _serviceState = MutableStateFlow<ServiceState>(ServiceState.Idle)
    val serviceState: StateFlow<ServiceState> get() = _serviceState

    private val _storePartUid = MutableStateFlow("")
    val storePartUid: StateFlow<String> get() = _storePartUid

    private val _pollingInterval = MutableStateFlow(1)
    val pollingInterval: StateFlow<Int> get() = _pollingInterval

    private val _pollingIntervalUnit = MutableStateFlow(0)
    val pollingIntervalUnit: StateFlow<Int> get() = _pollingIntervalUnit

    init {
        updateAutoStartState()
        updatePollingInterval()
        updateServiceState()
        loadStorePartUid()
    }

    fun saveAutoStartPreference(isEnabled: Boolean) {
        viewModelScope.launch {
            autoStartUseCase.saveAutoStartPreference(isEnabled)
            _autoStart.value = isEnabled
        }
    }

    fun savePollingInterval(value: Int, unit: Int) {
        viewModelScope.launch {
            val intervalInMilliseconds = when (unit) {
                0 -> value * 1000
                1 -> value * 60 * 1000
                2 -> value * 60 * 60 * 1000
                3 -> value * 24 * 60 * 60 * 1000
                else -> 0
            }
            pollingIntervalUseCase.savePollingInterval(intervalInMilliseconds)
            _pollingInterval.value = value
            _pollingIntervalUnit.value = unit
        }
    }

    fun startService(storePartUid: String) {
        viewModelScope.launch {
            handleServiceResult(
                result = productTrackingServiceUseCase.startService(storePartUid)
            )
        }
    }

    fun stopService() {
        viewModelScope.launch {
            handleServiceResult(
                result = productTrackingServiceUseCase.stopService()
            )
        }
    }

    fun saveStorePartUid(storePartUid: String) {
        viewModelScope.launch {
            storePartUseCase.saveStorePartUid(storePartUid)
            _storePartUid.value = storePartUid
        }
    }

    private fun updateAutoStartState() {
        viewModelScope.launch {
            _autoStart.value = autoStartUseCase.getAutoStartPreference()
        }
    }

    private fun updatePollingInterval() {
        viewModelScope.launch {
            val intervalInMilliseconds = pollingIntervalUseCase.getPollingInterval()

            val (value, unit) = when {
                intervalInMilliseconds < 1000 -> {
                    1 to 0
                }
                intervalInMilliseconds % (24 * 60 * 60 * 1000) == 0 -> {
                    intervalInMilliseconds / (24 * 60 * 60 * 1000) to 3
                }
                intervalInMilliseconds % (60 * 60 * 1000) == 0 -> {
                    intervalInMilliseconds / (60 * 60 * 1000) to 2
                }
                intervalInMilliseconds % (60 * 1000) == 0 -> {
                    intervalInMilliseconds / (60 * 1000) to 1
                }
                else -> {
                    intervalInMilliseconds / 1000 to 0
                }
            }
            _pollingInterval.value = value
            _pollingIntervalUnit.value = unit
        }
    }

    private fun updateServiceState() {
        viewModelScope.launch {
            handleServiceResult(
                result = productTrackingServiceUseCase.getServiceState()
            )
        }
    }

    private fun handleServiceResult(result: Result<ServiceState>) {
        _serviceState.value = result.fold(
            onSuccess = { it },
            onFailure = { ServiceState.Stopped(it.message) }
        )
    }

    private fun loadStorePartUid() {
        viewModelScope.launch {
            _storePartUid.value = storePartUseCase.getStorePartUid()
        }
    }
}
