package com.example.model

data class UserProfile(
    val username: String = "Flutter_Master",
    val avatarEmoji: String = "🚀",
    val xp: Int = 120,
    val level: Int = 1,
    val coins: Int = 100,
    val hearts: Int = 5,
    val maxHearts: Int = 5,
    val streakDays: Int = 3,
    val lastDailyClaimDate: Long = 0L,
    val isProSubscriber: Boolean = false,
    val completedLessonIds: Set<String> = emptySet(),
    val unlockedLessonIds: Set<String> = setOf("lesson_1", "lesson_2", "lesson_3"),
    val downloadedLessonIds: Set<String> = setOf("lesson_1"),
    val unlockedAchievementIds: Set<String> = setOf("first_step"),
    val claimedAchievementIds: Set<String> = emptySet(),
    val completedChallengeIds: Set<String> = emptySet(),
    val correctQuizzesCount: Int = 0,
    val totalQuizzesTaken: Int = 0,
    val isDarkMode: Boolean? = null // null means follow system
) {
    val levelTitle: String
        get() = when {
            level >= 10 -> "Flutter Gurusi 👑"
            level >= 7 -> "Senior Dart Dasturchi ⚡"
            level >= 5 -> "Middle Flutter Dev 🎯"
            level >= 3 -> "Junior Flutter Dev 🛠️"
            else -> "Boshlovchi Sayohatchi 🌱"
        }

    val xpForNextLevel: Int
        get() = level * 150

    val currentLevelProgress: Float
        get() {
            val xpInCurrentLevel = xp % 150
            return (xpInCurrentLevel.toFloat() / 150f).coerceIn(0f, 1f)
        }
}
