package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.LeaderboardEntry

@Composable
fun LeaderboardScreen(
    entries: List<LeaderboardEntry>,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Weekly, 1: All time
    var selectedPlayer by remember { mutableStateOf<LeaderboardEntry?>(null) }

    val topThree = entries.take(3)
    val remainingEntries = entries.drop(3)

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("leaderboard_screen")
    ) {
        // HEADER & TAB BAR
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Liderlar Jadvali 🏆",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Eng yaxshi Flutter dasturchilari bilan raqobatlashing",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.clip(RoundedCornerShape(12.dp))
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Haftalik Musobaqa 🔥", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Barcha Vaqtlar 👑", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 8.dp, 16.dp, 96.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // PODIUM TOP 3
            if (topThree.isNotEmpty()) {
                item {
                    PodiumView(
                        topEntries = topThree,
                        onPlayerClick = { selectedPlayer = it }
                    )
                }
            }

            item {
                Text(
                    text = "Barcha Ishtirokchilar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            // LIST OF REMAINING USERS
            items(remainingEntries, key = { it.id }) { entry ->
                LeaderboardRowItem(
                    entry = entry,
                    onClick = { selectedPlayer = entry }
                )
            }
        }

        // PLAYER INSPECT DIALOG
        if (selectedPlayer != null) {
            PlayerDetailsDialog(
                player = selectedPlayer!!,
                onDismiss = { selectedPlayer = null }
            )
        }
    }
}

@Composable
fun PodiumView(
    topEntries: List<LeaderboardEntry>,
    onPlayerClick: (LeaderboardEntry) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0F172A)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "TOP REYTING POG'ONASI",
                color = Color(0xFF38BDF8),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                // 2nd Place (Silver)
                if (topEntries.size > 1) {
                    PodiumColumnItem(
                        entry = topEntries[1],
                        place = 2,
                        pedestalHeight = 85.dp,
                        accentColor = Color(0xFF94A3B8),
                        trophyEmoji = "🥈",
                        onClick = { onPlayerClick(topEntries[1]) }
                    )
                }

                // 1st Place (Gold)
                if (topEntries.isNotEmpty()) {
                    PodiumColumnItem(
                        entry = topEntries[0],
                        place = 1,
                        pedestalHeight = 115.dp,
                        accentColor = Color(0xFFF59E0B),
                        trophyEmoji = "🥇",
                        onClick = { onPlayerClick(topEntries[0]) }
                    )
                }

                // 3rd Place (Bronze)
                if (topEntries.size > 2) {
                    PodiumColumnItem(
                        entry = topEntries[2],
                        place = 3,
                        pedestalHeight = 65.dp,
                        accentColor = Color(0xFFD97706),
                        trophyEmoji = "🥉",
                        onClick = { onPlayerClick(topEntries[2]) }
                    )
                }
            }
        }
    }
}

@Composable
fun PodiumColumnItem(
    entry: LeaderboardEntry,
    place: Int,
    pedestalHeight: androidx.compose.ui.unit.Dp,
    accentColor: Color,
    trophyEmoji: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(96.dp)
            .clickable { onClick() }
    ) {
        Text(text = trophyEmoji, fontSize = 20.sp)
        Text(
            text = entry.avatarEmoji,
            fontSize = 32.sp,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        Text(
            text = if (entry.isCurrentUser) "Siz" else entry.username,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            color = if (entry.isCurrentUser) Color(0xFF38BDF8) else Color.White,
            maxLines = 1
        )
        Text(
            text = "${entry.xp} XP",
            fontSize = 10.sp,
            color = accentColor,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Pedestal Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(pedestalHeight)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(accentColor.copy(alpha = 0.8f), accentColor.copy(alpha = 0.3f))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$place",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
        }
    }
}

@Composable
fun LeaderboardRowItem(
    entry: LeaderboardEntry,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag("leaderboard_row_${entry.rank}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (entry.isCurrentUser) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (entry.isCurrentUser) MaterialTheme.colorScheme.primary else Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank Number
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${entry.rank}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Avatar Emoji
            Text(text = entry.avatarEmoji, fontSize = 22.sp)

            Spacer(modifier = Modifier.width(10.dp))

            // Username & League
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = entry.username,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (entry.isCurrentUser) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(start = 6.dp)
                        ) {
                            Text(
                                text = "SIZ",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
                Text(
                    text = "${entry.league} Ligasi • ${entry.completedLessons} dars",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // XP Badge
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFF0284C7).copy(alpha = 0.12f)
            ) {
                Text(
                    text = "${entry.xp} XP",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color(0xFF0284C7),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun PlayerDetailsDialog(
    player: LeaderboardEntry,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = player.avatarEmoji, fontSize = 48.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = player.username,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${player.league} Ligasi • Reyting #${player.rank}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "XP Bal", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "${player.xp}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Darslar", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "${player.completedLessons}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Holat", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "Faol 🟢", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Yopish")
                }
            }
        }
    }
}
