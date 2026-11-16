package com.ynz.gacor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var loggedIn by remember { mutableStateOf(false) }
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFF00FF88),
                    primaryContainer = Color(0xFF0E2A1E),
                    surfaceVariant = Color(0xFF11201A)
                )
            ) {
                Surface {
                    if (loggedIn) DashboardScreen() else LoginScreen { loggedIn = true }
                }
            }
        }
    }
}
