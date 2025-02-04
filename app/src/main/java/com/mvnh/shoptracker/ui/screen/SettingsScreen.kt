@file:OptIn(ExperimentalMaterial3Api::class)

package com.mvnh.shoptracker.ui.screen

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mvnh.shoptracker.R
import com.mvnh.shoptracker.domain.state.ServiceState
import com.mvnh.shoptracker.ui.viewmodel.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
) {
    val autoStartPreference by viewModel.autoStart.collectAsState()
    val serviceState by viewModel.serviceState.collectAsState()
    val storePartUid by viewModel.storePartUid.collectAsState()
    val pollingIntervalValue by viewModel.pollingInterval.collectAsState()
    val pollingIntervalUnit by viewModel.pollingIntervalUnit.collectAsState()
    var isPollingDropdownExpanded by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    val isEnabled = serviceState != ServiceState.Running

    LaunchedEffect(serviceState) {
        if (serviceState is ServiceState.Stopped) {
            (serviceState as? ServiceState.Stopped)?.message?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = { AppTopBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SettingsSection(titleRes = R.string.query_params) {
                TextField(
                    value = storePartUid,
                    onValueChange = viewModel::saveStorePartUid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    enabled = isEnabled,
                    label = { Text(text = stringResource(id = R.string.store_part_uid)) },
                    singleLine = true
                )
            }

            SettingsSection(titleRes = R.string.polling_interval) {
                PollingIntervalInput(
                    value = pollingIntervalValue.toString(),
                    onValueChange = { newValue ->
                        viewModel.savePollingInterval(newValue.toIntOrNull() ?: 1, pollingIntervalUnit)
                    },
                    selectedUnit = pollingIntervalUnit,
                    onUnitChange = { newUnit ->
                        viewModel.savePollingInterval(pollingIntervalValue, newUnit)
                    },
                    isDropdownExpanded = isPollingDropdownExpanded,
                    onDropdownChange = { isPollingDropdownExpanded = it },
                    enabled = isEnabled
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.auto_start))

                Spacer(modifier = Modifier.weight(1f))

                Switch(
                    checked = autoStartPreference,
                    onCheckedChange = viewModel::saveAutoStartPreference,
                    enabled = isEnabled
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            ServiceControlButton(
                serviceState = serviceState,
                onStart = { viewModel.startService(storePartUid) },
                onStop = viewModel::stopService,
                enabled = storePartUid.isNotBlank() && pollingIntervalValue > 0
            )
        }
    }
}

@Composable
private fun AppTopBar() {
    val context = LocalContext.current
    val versionName = runCatching {
        context.packageManager.getPackageInfo(context.packageName, 0).versionName
    }.getOrNull() ?: "N/A"

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            Text(text = versionName)
        }
    )
}

@Composable
fun SettingsSection(
    @StringRes titleRes: Int,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )

        content()
    }
}

@Composable
fun PollingIntervalInput(
    value: String,
    onValueChange: (String) -> Unit,
    selectedUnit: Int,
    onUnitChange: (Int) -> Unit,
    isDropdownExpanded: Boolean,
    onDropdownChange: (Boolean) -> Unit,
    enabled: Boolean
) {
    val units = listOf(
        R.string.seconds,
        R.string.minutes,
        R.string.hours,
        R.string.days
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp),
            enabled = enabled,
            label = { Text(text = stringResource(id = R.string.quantity)) },
            singleLine = true
        )

        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { if (enabled) onDropdownChange(it) },
            modifier = Modifier.weight(1f)
        ) {
            TextField(
                value = stringResource(id = units[selectedUnit]),
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                trailingIcon = {
                    Icon(
                        imageVector = if (isDropdownExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .menuAnchor(
                        type = MenuAnchorType.PrimaryNotEditable,
                        enabled = enabled
                    )
                    .fillMaxWidth()
                    .padding(start = 4.dp)
            )
            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { onDropdownChange(false) }
            ) {
                units.forEachIndexed { index, unit ->
                    DropdownMenuItem(
                        onClick = {
                            onUnitChange(index)
                            onDropdownChange(false)
                        },
                        enabled = enabled,
                        text = { Text(text = stringResource(id = unit)) }
                    )
                }
            }
        }
    }
}

@Composable
fun ServiceControlButton(
    serviceState: ServiceState,
    onStart: () -> Unit,
    onStop: () -> Unit,
    enabled: Boolean
) {
    val buttonModifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .safeDrawingPadding()

    when (serviceState) {
        ServiceState.Running -> {
            OutlinedButton(
                onClick = onStop,
                modifier = buttonModifier,
                enabled = enabled
            ) {
                Text(text = stringResource(R.string.stop_service))
            }
        }
        else -> {
            Button(
                onClick = onStart,
                modifier = buttonModifier,
                enabled = enabled
            ) {
                Text(text = stringResource(R.string.start_service))
            }
        }
    }
}
