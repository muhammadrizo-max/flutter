package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Lesson
import com.example.model.UserProfile
import com.example.ui.MainViewModel

@Composable
fun LessonsScreen(
    viewModel: MainViewModel,
    profile: UserProfile,
    allLessons: List<Lesson>,
    filterOnlyOffline: Boolean,
    selectedModuleFilter: Int?,
    onSelectLesson: (Lesson) -> Unit,
    modifier: Modifier = Modifier
) {
    val completedCount = profile.completedLessonIds.size
    val totalCount = allLessons.size
    val progressFraction = if (totalCount > 0) completedCount.toFloat() / totalCount.toFloat() else 0f

    val filteredLessons = allLessons.filter { lesson ->
        val matchesOffline = !filterOnlyOffline || profile.downloadedLessonIds.contains(lesson.id)
        val matchesModule = selectedModuleFilter == null || lesson.moduleNumber == selectedModuleFilter
        matchesOffline && matchesModule
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("lessons_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // HERO BANNER
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF0F172A), Color(0xFF02569B))
                        )
                    )
            ) {
                // Optional Generated Hero Graphic
                Image(
                    painter = painterResource(id = R.drawable.flutter_quest_hero_1791449965902),
                    contentDescription = "Flutter Quest Banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop,
                    alpha = 0.45f
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF0284C7).copy(alpha = 0.8f)
                        ) {
                            Text(
                                text = "FLUTTER BILAN DASTURLASH",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        if (profile.isProSubscriber) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFF59E0B)
                            ) {
                                Text(
                                    text = "👑 PRO VIP",
                                    color = Color.Black,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "O'yin orqali Flutter o'rganing! 🎮",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Interaktiv kodlar, testlar va cheksiz imkoniyatlar",
                        color = Color(0xFFBAE6FD),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                    )

                    // Overall Progress Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Umumiy natija: $completedCount/$totalCount dars",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${(progressFraction * 100).toInt()}%",
                            color = Color(0xFF38BDF8),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { progressFraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFF38BDF8),
                        trackColor = Color(0xFF1E293B)
                    )
                }
            }
        }

        // FILTER CHIPS ROW
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = !filterOnlyOffline && selectedModuleFilter == null,
                        onClick = {
                            viewModel.setFilterOnlyOffline(false)
                            viewModel.setSelectedModuleFilter(null)
                        },
                        label = { Text("Barchasi") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.AllInclusive, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    )
                }

                item {
                    FilterChip(
                        selected = filterOnlyOffline,
                        onClick = {
                            viewModel.setFilterOnlyOffline(!filterOnlyOffline)
                        },
                        label = { Text("💾 Oflayn (${profile.downloadedLessonIds.size})") }
                    )
                }

                item {
                    FilterChip(
                        selected = selectedModuleFilter == 1,
                        onClick = {
                            viewModel.setSelectedModuleFilter(if (selectedModuleFilter == 1) null else 1)
                        },
                        label = { Text("1-Modul (Asoslar)") }
                    )
                }

                item {
                    FilterChip(
                        selected = selectedModuleFilter == 2,
                        onClick = {
                            viewModel.setSelectedModuleFilter(if (selectedModuleFilter == 2) null else 2)
                        },
                        label = { Text("2-Modul (Joylashuv)") }
                    )
                }

                item {
                    FilterChip(
                        selected = selectedModuleFilter == 3,
                        onClick = {
                            viewModel.setSelectedModuleFilter(if (selectedModuleFilter == 3) null else 3)
                        },
                        label = { Text("3-Modul (Holat)") }
                    )
                }
            }
        }

        // LESSONS LIST GROUPED OR FILTERED
        if (filteredLessons.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "📭", fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (filterOnlyOffline) "Oflaynda darslar yo'q" else "Darslar topilmadi",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = if (filterOnlyOffline) "Darslarni oflayn o'rganish uchun yuklab oling!" else "Filtrni o'zgartirib ko'ring",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(filteredLessons, key = { it.id }) { lesson ->
                val isCompleted = profile.completedLessonIds.contains(lesson.id)
                val isUnlocked = lesson.isFree || profile.isProSubscriber || profile.unlockedLessonIds.contains(lesson.id)
                val isDownloaded = profile.downloadedLessonIds.contains(lesson.id)

                LessonCardItem(
                    lesson = lesson,
                    isCompleted = isCompleted,
                    isUnlocked = isUnlocked,
                    isDownloaded = isDownloaded,
                    isPro = profile.isProSubscriber,
                    onCardClick = { onSelectLesson(lesson) },
                    onToggleDownload = { viewModel.toggleDownloadLesson(lesson.id) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun LessonCardItem(
    lesson: Lesson,
    isCompleted: Boolean,
    isUnlocked: Boolean,
    isDownloaded: Boolean,
    isPro: Boolean,
    onCardClick: () -> Unit,
    onToggleDownload: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable { onCardClick() }
            .testTag("lesson_card_${lesson.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.5.dp,
            when {
                isCompleted -> Color(0xFF10B981)
                isUnlocked -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                else -> Color(0xFFCBD5E1).copy(alpha = 0.4f)
            }
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Lesson Status Icon Indicator
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isCompleted -> Color(0xFF10B981)
                            isUnlocked -> Color(0xFF0284C7)
                            else -> Color(0xFF64748B).copy(alpha = 0.3f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isCompleted -> Icon(imageVector = Icons.Default.Check, contentDescription = "Tugallangan", tint = Color.White)
                    isUnlocked -> Text(text = "${lesson.orderNumber}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    else -> Icon(imageVector = Icons.Default.Lock, contentDescription = "Qulflangan", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Title & Details
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = lesson.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    if (lesson.isFree) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF10B981).copy(alpha = 0.15f),
                            modifier = Modifier.padding(start = 6.dp)
                        ) {
                            Text(
                                text = "BEPUL",
                                color = Color(0xFF059669),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Text(
                    text = lesson.subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp, bottom = 6.dp)
                )

                // Reward chips & Offline badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "⭐ +${lesson.xpReward} XP",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0284C7)
                    )
                    Text(
                        text = "🪙 +${lesson.coinReward}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFF59E0B)
                    )

                    if (isDownloaded) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF0284C7).copy(alpha = 0.15f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(text = "💾 Oflayn", fontSize = 10.sp, color = Color(0xFF0284C7), fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }

            // Quick Download Button
            IconButton(
                onClick = onToggleDownload,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = if (isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                    contentDescription = "Oflayn saqlash",
                    tint = if (isDownloaded) Color(0xFF0284C7) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
