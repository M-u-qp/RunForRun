package com.example.runforrun.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.runforrun.common.extension.allPermissions
import com.example.runforrun.common.extension.appSettings
import com.example.runforrun.common.extension.locationPermission
import com.example.runforrun.common.utils.PermissionUts
import com.example.runforrun.ui.components.PermissionDialog
import com.example.runforrun.ui.navgraph.NavGraph
import com.example.runforrun.ui.theme.RunForRunTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RunForRunTheme {
                PermissionRequest()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(startDestination = viewModel.startDestination)
                }
            }
        }
    }

    @Composable
    private fun PermissionRequest() {
        var showPermissionReason by rememberSaveable { mutableStateOf(false) }
        var showReason by rememberSaveable { mutableStateOf(false) }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = {
                it.forEach { (permission, granted) ->
                    if (!granted && PermissionUts.locationPermissions.contains(permission)) {
                        showPermissionReason = true
                    }
                }
            }
        )
        if (showPermissionReason) {
            PermissionDialog(
                dismissClick = {
                    if (!locationPermission()) {
                        finish()
                    } else {
                        showPermissionReason = false
                    }
                },
                okClick = { appSettings() }
            )
        }
        if (showReason) {
            PermissionDialog(
                dismissClick = ::finish,
                okClick = {
                    showReason = false
                    launcher.launch(PermissionUts.allPermissions)
                }
            )
        }
        LaunchedEffect(key1 = Unit) {
            when {
                allPermissions() -> return@LaunchedEffect
                PermissionUts.locationPermissions.any {
                    shouldShowRequestPermissionRationale(
                        it
                    )
                } ->
                    showReason = true

                else -> {
                    Toast.makeText(
                        this@MainActivity,
                        "Пожалуйста, включите GPS, чтобы получать статистику бега.",
                        Toast.LENGTH_LONG
                    ).show()
                    launcher.launch(PermissionUts.allPermissions)
                }
            }
        }
    }
}
