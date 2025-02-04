package com.mvnh.shoptracker

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.mvnh.shoptracker.di.App
import com.mvnh.shoptracker.ui.theme.ShopTrackerTheme

class MainActivity : ComponentActivity() {

    private lateinit var requestPostNotificationsPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        requestPostNotificationsPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    Toast.makeText(
                        this,
                        getString(R.string.post_notifications_permission_granted),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.post_notifications_permission_declined),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        requestPostNotificationsPermission()

        setContent {
            ShopTrackerTheme {
                App()
            }
        }
    }

    private fun requestPostNotificationsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    //
                }

                shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) -> {
                    Toast.makeText(
                        this,
                        getString(R.string.post_notifications_permissions_required),
                        Toast.LENGTH_SHORT
                    ).show()

                    requestPostNotificationsPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }

                else -> {
                    requestPostNotificationsPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            //
        }
    }
}
