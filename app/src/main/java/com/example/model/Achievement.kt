package com.example.model

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val coinReward: Int,
    val xpReward: Int,
    val currentProgress: Int,
    val maxProgress: Int,
    val isCompleted: Boolean,
    val isClaimed: Boolean
)

data class LeaderboardEntry(
    val id: String,
    val rank: Int,
    val username: String,
    val avatarEmoji: String,
    val xp: Int,
    val league: String, // "Olmos", "Oltin", "Kumush", "Bronza"
    val completedLessons: Int,
    val isCurrentUser: Boolean = false
)
