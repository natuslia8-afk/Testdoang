package com.ynz.gacor

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

data class Tool(val name: String, val emoji: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val ctx = LocalContext.current
    var dialogTitle by remember { mutableStateOf<String?>(null) }
    var fakeCrash by remember { mutableStateOf(false) }

    val tools = listOf(
        Tool("Getar Brutal", "📳"), Tool("Dialog Troll", "💬"),
        Tool("Fake Crash", "💥"), Tool("Loading 10s", "⏳"),
        Tool("Layar Hitam", "⬛"), Tool("Spam Toast", "🔔"),
        Tool("Getar + Dialog", "⚡"), Tool("Countdown", "⏱️"),
        Tool("Matrix Text", "🟩"), Tool("Reset", "🔄")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard YNZ", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { pad ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(pad).fillMaxSize().padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(tools) { tool ->
                Card(
                    onClick = {
                        when (tool.name) {
                            "Getar Brutal" -> vibrate(ctx, 2000)
                            "Dialog Troll" -> dialogTitle = "Wkwk bisa apa lu 😹"
                            "Fake Crash" -> fakeCrash = true
                            "Loading 10s" -> dialogTitle = "Loading... (efek lokal)"
                            "Layar Hitam" -> dialogTitle = "Layar hitam 3 detik (lokal)"
                            "Spam Toast" -> dialogTitle = "Toast spam simulasi"
                            "Getar + Dialog" -> { vibrate(ctx, 1200); dialogTitle = "Getar + dialog 😈" }
                            "Countdown" -> dialogTitle = "3... 2... 1... 💥 (simulasi)"
                            "Matrix Text" -> dialogTitle = "01011001 01001110 01011010"
                            "Reset" -> { dialogTitle = "Reset. Aman." }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(tool.emoji, fontSize = 30.sp)
                        Spacer(Modifier.height(8.dp))
                        Text(tool.name, fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center, fontSize = 14.sp)
                    }
                }
            }
        }
    }

    dialogTitle?.let { title ->
        AlertDialog(
            onDismissRequest = { dialogTitle = null },
            title = { Text(title) },
            text = { Text("Efek ini cuma jalan di HP kamu sendiri. Simulasi lokal.") },
            confirmButton = {
                TextButton(onClick = { dialogTitle = null }) { Text("Oke") }
            }
        )
    }

    if (fakeCrash) {
        var count by remember { mutableStateOf(0) }
        LaunchedEffect(Unit) {
            while (count < 4) { vibrate(ctx, 300); delay(700); count++ }
            fakeCrash = false
        }
        AlertDialog(
            onDismissRequest = {},
            title = { Text("💥 APP CRASH (SIMULASI)") },
            text = { Text("Ini cuma layar fake crash. App tetap hidup. Efek lokal.") },
            confirmButton = {
                TextButton(onClick = { fakeCrash = false }) { Text("Tutup") }
            }
        )
    }
}

fun vibrate(ctx: Context, ms: Long) {
    val v = ctx.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        v.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION") v.vibrate(ms)
    }
}
