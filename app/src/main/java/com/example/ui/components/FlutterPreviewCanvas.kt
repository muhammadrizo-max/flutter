package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SimulatedWidgetType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FlutterPreviewCanvas(
    widgetType: SimulatedWidgetType,
    codeText: String,
    modifier: Modifier = Modifier
) {
    var liveCounter by remember { mutableIntStateOf(0) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    var navigatorPage by remember { mutableIntStateOf(1) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF0F172A))
            .border(2.dp, Color(0xFF0284C7), RoundedCornerShape(20.dp))
            .padding(12.dp)
            .testTag("flutter_preview_canvas")
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Simulated Device Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF22C55E))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "FLUTTER LIVE RUNTIME",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF38BDF8),
                        letterSpacing = 1.sp
                    )
                }
                Text(
                    text = "Hot Reload ⚡",
                    fontSize = 11.sp,
                    color = Color(0xFFFBBF24),
                    fontWeight = FontWeight.Medium
                )
            }

            // Simulated Mobile Phone Screen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White)
                    .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Flutter AppBar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .background(Color(0xFF02569B))
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (widgetType == SimulatedWidgetType.APP_BAR_SCAFFOLD && navigatorPage == 2) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Orqaga",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable { navigatorPage = 1 }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text(
                                text = when (widgetType) {
                                    SimulatedWidgetType.APP_BAR_SCAFFOLD -> if (navigatorPage == 1) "Bosh Sahifa" else "Tafsilotlar Sahifasi"
                                    SimulatedWidgetType.COUNTER_APP -> "Flutter Demo Home"
                                    SimulatedWidgetType.LIST_VIEW -> "Flutter Ro'yxati"
                                    else -> "Flutter Ilovasi"
                                },
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Flutter Scaffold Body
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color(0xFFF8FAFC)),
                        contentAlignment = Alignment.Center
                    ) {
                        when (widgetType) {
                            SimulatedWidgetType.TEXT_ONLY -> {
                                val displayText = if (codeText.contains("Flutter")) {
                                    "Salom Flutter! 💙"
                                } else {
                                    "Flutter olamiga xush kelibsiz!"
                                }
                                Text(
                                    text = displayText,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF02569B),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                            SimulatedWidgetType.CONTAINER_TEXT -> {
                                Box(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFF0284C7))
                                        .padding(horizontal = 24.dp, vertical = 14.dp)
                                ) {
                                    Text(
                                        text = "Flutter Qutisi 📦",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                }
                            }

                            SimulatedWidgetType.ROW_ICONS -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Row(children: [...])",
                                        fontSize = 12.sp,
                                        color = Color(0xFF64748B),
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Row(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color(0xFFE0F2FE))
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = "Yulduz",
                                            tint = Color(0xFFF59E0B),
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = "Yurak",
                                            tint = Color(0xFFEF4444),
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Icon(
                                            imageVector = Icons.Default.ThumbUp,
                                            contentDescription = "Loyiha",
                                            tint = Color(0xFF3B82F6),
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }
                            }

                            SimulatedWidgetType.COLUMN_BUTTON -> {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Button(
                                        onClick = {
                                            snackbarMessage = "🎉 SnackBar: Tugma bosildi!"
                                            coroutineScope.launch {
                                                delay(2500)
                                                snackbarMessage = null
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF0284C7)
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.TouchApp,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Bosing (Tap me)")
                                    }
                                }
                            }

                            SimulatedWidgetType.COUNTER_APP -> {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Tugma bosilganlar soni:",
                                        fontSize = 12.sp,
                                        color = Color(0xFF64748B)
                                    )
                                    Text(
                                        text = "$liveCounter",
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF02569B)
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    IconButton(
                                        onClick = { liveCounter++ },
                                        modifier = Modifier
                                            .size(42.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF0284C7))
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Oshirish",
                                            tint = Color.White
                                        )
                                    }
                                }
                            }

                            SimulatedWidgetType.LIST_VIEW -> {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(6.dp)
                                ) {
                                    items(5) { index ->
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 2.dp),
                                            color = Color.White,
                                            shape = RoundedCornerShape(6.dp),
                                            shadowElevation = 1.dp
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(8.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.School,
                                                    contentDescription = null,
                                                    tint = Color(0xFF0284C7),
                                                    modifier = Modifier.size(20.dp)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = "Dars #${index + 1}: Flutter Asoslari",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    color = Color(0xFF1E293B)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            SimulatedWidgetType.APP_BAR_SCAFFOLD -> {
                                if (navigatorPage == 1) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "1-Sahifadasiz 🧭",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1E293B)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Button(
                                            onClick = { navigatorPage = 2 },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF0284C7)
                                            ),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text("2-Sahifaga o'tish (Push)")
                                        }
                                    }
                                } else {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "2-Sahifa: DetailScreen 🎯",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1E293B)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        OutlinedButton(
                                            onClick = { navigatorPage = 1 },
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text("Orqaga qaytish (Pop)")
                                        }
                                    }
                                }
                            }
                        }

                        // Simulated SnackBar Overlay
                        if (snackbarMessage != null) {
                            Surface(
                                color = Color(0xFF1E293B),
                                shape = RoundedCornerShape(6.dp),
                                shadowElevation = 4.dp,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = snackbarMessage ?: "",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
