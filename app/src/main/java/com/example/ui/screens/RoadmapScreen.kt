package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Lesson
import com.example.model.UserProfile
import com.example.ui.MainViewModel

@Composable
fun RoadmapScreen(
    viewModel: MainViewModel,
    profile: UserProfile,
    allLessons: List<Lesson>,
    onSelectLesson: (Lesson) -> Unit,
    modifier: Modifier = Modifier
) {
    val completedCount = profile.completedLessonIds.size
    val totalCount = allLessons.size
    val progressFraction = if (totalCount > 0) completedCount.toFloat() / totalCount.toFloat() else 0f

    // Animated floating effect for islands
    val infiniteTransition = rememberInfiniteTransition(label = "island_float")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float_y"
    )

    // Pulsing halo for current active level
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0A1128),
                        Color(0xFF0F1E40),
                        Color(0xFF02569B)
                    )
                )
            )
            .testTag("roadmap_screen")
    ) {
        // BACKGROUND FLOATING PARTICLES & STARS
        Canvas(modifier = Modifier.fillMaxSize()) {
            val starPositions = listOf(
                Offset(size.width * 0.15f, size.height * 0.12f),
                Offset(size.width * 0.85f, size.height * 0.18f),
                Offset(size.width * 0.25f, size.height * 0.35f),
                Offset(size.width * 0.78f, size.height * 0.48f),
                Offset(size.width * 0.12f, size.height * 0.65f),
                Offset(size.width * 0.88f, size.height * 0.78f),
                Offset(size.width * 0.35f, size.height * 0.90f)
            )
            starPositions.forEach { pos ->
                drawCircle(
                    color = Color(0xFF67E8F9).copy(alpha = 0.35f),
                    radius = 4f,
                    center = pos
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // PROGRESS HEADER BANNER (LIKE IMAGE 3)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E293B).copy(alpha = 0.9f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF38BDF8).copy(alpha = 0.4f)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF0284C7)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = profile.avatarEmoji, fontSize = 28.sp)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "O'quv Xaritasi: ${(progressFraction * 100).toInt()}%",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "$completedCount/$totalCount Daraja",
                                    color = Color(0xFF38BDF8),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            LinearProgressIndicator(
                                progress = { progressFraction },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = Color(0xFF38BDF8),
                                trackColor = Color(0xFF334155)
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // ROADMAP FLOATING ISLANDS
            itemsIndexed(allLessons, key = { _, it -> it.id }) { index, lesson ->
                val isCompleted = profile.completedLessonIds.contains(lesson.id)
                val isUnlocked = lesson.isFree || profile.isProSubscriber || profile.unlockedLessonIds.contains(lesson.id)
                val isCurrentActive = isUnlocked && !isCompleted

                // Alternate horizontal alignment: Center-Left, Center-Right, Center
                val alignmentBias = when (index % 4) {
                    0 -> 0.2f  // Left
                    1 -> 0.5f  // Center
                    2 -> 0.8f  // Right
                    else -> 0.5f // Center
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // CONNECTOR BRIDGE (BETWEEN ISLANDS)
                    if (index > 0) {
                        Canvas(
                            modifier = Modifier
                                .width(90.dp)
                                .height(44.dp)
                        ) {
                            val startX = size.width / 2
                            val endX = size.width / 2
                            drawLine(
                                color = if (isCompleted || isCurrentActive) Color(0xFF38BDF8) else Color(0xFF475569),
                                start = Offset(startX, 0f),
                                end = Offset(endX, size.height),
                                strokeWidth = 6f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f)
                            )
                        }
                    }

                    // THE FLOATING ISLAND NODE
                    FloatingIslandNode(
                        lesson = lesson,
                        levelNumber = index + 1,
                        isCompleted = isCompleted,
                        isUnlocked = isUnlocked,
                        isCurrentActive = isCurrentActive,
                        floatY = if (index % 2 == 0) floatOffset else -floatOffset,
                        pulseScale = pulseScale,
                        onClick = { onSelectLesson(lesson) },
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
                // FINAL TROPHY PODIUM
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFF59E0B))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "👑", fontSize = 40.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Flutter Magistri Marrasi",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFBBF24),
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Barcha 15 darajani yakunlab sertifikat va Olmos ligasiga kiring!",
                            color = Color(0xFF94A3B8),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FloatingIslandNode(
    lesson: Lesson,
    levelNumber: Int,
    isCompleted: Boolean,
    isUnlocked: Boolean,
    isCurrentActive: Boolean,
    floatY: Float,
    pulseScale: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Color palettes for different stages
    val islandGradient = when {
        isCompleted -> listOf(Color(0xFF059669), Color(0xFF10B981), Color(0xFF047857))
        isCurrentActive -> listOf(Color(0xFF0284C7), Color(0xFF00B4D8), Color(0xFF0369A1))
        isUnlocked -> listOf(Color(0xFF6366F1), Color(0xFF4F46E5), Color(0xFF4338CA))
        else -> listOf(Color(0xFF1E293B), Color(0xFF334155), Color(0xFF0F172A))
    }

    val iconEmoji = when (levelNumber) {
        1 -> "🚀"
        2 -> "💻"
        3 -> "🔀"
        4 -> "📦"
        5 -> "⏳"
        6 -> "🏛️"
        7 -> "🛡️"
        8 -> "📱"
        9 -> "🎨"
        10 -> "📑"
        11 -> "🧭"
        12 -> "⚡"
        13 -> "💾"
        14 -> "🌐"
        15 -> "🏆"
        else -> "⭐"
    }

    Column(
        modifier = modifier
            .offset(y = floatY.dp)
            .clickable { onClick() }
            .testTag("island_node_level_$levelNumber"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ACTIVE HALO EFFECT
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (isCurrentActive) {
                Box(
                    modifier = Modifier
                        .size((78 * pulseScale).dp)
                        .clip(CircleShape)
                        .background(Color(0xFF38BDF8).copy(alpha = 0.25f))
                )
            }

            // PORTAL / BEACON ORB
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(islandGradient)
                    )
                    .border(
                        3.dp,
                        if (isCurrentActive) Color(0xFFFBBF24) else if (isCompleted) Color(0xFF34D399) else Color(0xFF64748B),
                        CircleShape
                    )
                    .shadow(8.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Bajarildi",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    } else if (!isUnlocked) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Qulflangan",
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(26.dp)
                        )
                    } else {
                        Text(
                            text = iconEmoji,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "$levelNumber",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ISLAND BASE CARD
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF0F172A).copy(alpha = 0.85f),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (isCurrentActive) Color(0xFF38BDF8) else Color(0xFF334155)
            ),
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Level $levelNumber: ${lesson.title.replace("Level $levelNumber: ", "")}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                    if (lesson.isFree) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFF10B981).copy(alpha = 0.2f),
                            modifier = Modifier.padding(start = 6.dp)
                        ) {
                            Text(
                                text = "BEPUL",
                                color = Color(0xFF34D399),
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Text(
                        text = "+${lesson.xpReward} XP ⭐",
                        fontSize = 10.sp,
                        color = Color(0xFF38BDF8),
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "+${lesson.coinReward} 🪙",
                        fontSize = 10.sp,
                        color = Color(0xFFFBBF24),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
