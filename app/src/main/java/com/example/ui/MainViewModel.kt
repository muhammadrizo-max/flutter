package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.LessonRepository
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AppNavScreen {
    LESSONS,
    STUDY_LESSON,
    LEADERBOARD,
    PROFILE
}

data class ChallengeFeedback(
    val isSuccess: Boolean,
    val message: String,
    val detail: String
)

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LessonRepository(application)

    private val _userProfile = MutableStateFlow(repository.getUserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _currentScreen = MutableStateFlow(AppNavScreen.LESSONS)
    val currentScreen: StateFlow<AppNavScreen> = _currentScreen.asStateFlow()

    private val _activeLesson = MutableStateFlow<Lesson?>(null)
    val activeLesson: StateFlow<Lesson?> = _activeLesson.asStateFlow()

    // 0: Theory, 1: Quiz, 2: Coding Exercise
    private val _studyStep = MutableStateFlow(0)
    val studyStep: StateFlow<Int> = _studyStep.asStateFlow()

    // Quiz states: questionId -> chosen answer index
    private val _quizSelectedAnswers = MutableStateFlow<Map<String, Int>>(emptyMap())
    val quizSelectedAnswers: StateFlow<Map<String, Int>> = _quizSelectedAnswers.asStateFlow()

    private val _quizAnswerConfirmed = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val quizAnswerConfirmed: StateFlow<Map<String, Boolean>> = _quizAnswerConfirmed.asStateFlow()

    // Code Editor
    private val _codeEditorText = MutableStateFlow("")
    val codeEditorText: StateFlow<String> = _codeEditorText.asStateFlow()

    private val _challengeFeedback = MutableStateFlow<ChallengeFeedback?>(null)
    val challengeFeedback: StateFlow<ChallengeFeedback?> = _challengeFeedback.asStateFlow()

    // Filters
    private val _filterOnlyOffline = MutableStateFlow(false)
    val filterOnlyOffline: StateFlow<Boolean> = _filterOnlyOffline.asStateFlow()

    private val _selectedModuleFilter = MutableStateFlow<Int?>(null)
    val selectedModuleFilter: StateFlow<Int?> = _selectedModuleFilter.asStateFlow()

    // Dialogs
    private val _showStoreDialog = MutableStateFlow(false)
    val showStoreDialog: StateFlow<Boolean> = _showStoreDialog.asStateFlow()

    private val _showAdDialog = MutableStateFlow(false)
    val showAdDialog: StateFlow<Boolean> = _showAdDialog.asStateFlow()
    var adTargetLessonId: String? = null
    var adPurpose: String = "+50 Tanga Mukofoti"

    private val _unlockTargetLesson = MutableStateFlow<Lesson?>(null)
    val unlockTargetLesson: StateFlow<Lesson?> = _unlockTargetLesson.asStateFlow()

    private val _showCelebration = MutableStateFlow(false)
    val showCelebration: StateFlow<Boolean> = _showCelebration.asStateFlow()
    var celebrationXp = 0
    var celebrationCoins = 0

    val allLessons: List<Lesson> = repository.allLessons

    fun navigateTo(screen: AppNavScreen) {
        _currentScreen.value = screen
    }

    fun openLesson(lesson: Lesson) {
        val profile = _userProfile.value
        val isUnlocked = lesson.isFree || profile.isProSubscriber || profile.unlockedLessonIds.contains(lesson.id)
        if (!isUnlocked) {
            _unlockTargetLesson.value = lesson
            return
        }

        _activeLesson.value = lesson
        _studyStep.value = 0
        _quizSelectedAnswers.value = emptyMap()
        _quizAnswerConfirmed.value = emptyMap()
        _codeEditorText.value = lesson.codingChallenge.startingCode
        _challengeFeedback.value = null
        _currentScreen.value = AppNavScreen.STUDY_LESSON
    }

    fun setStudyStep(step: Int) {
        _studyStep.value = step.coerceIn(0, 2)
    }

    fun selectQuizOption(questionId: String, optionIndex: Int) {
        val current = _quizSelectedAnswers.value.toMutableMap()
        current[questionId] = optionIndex
        _quizSelectedAnswers.value = current
    }

    fun submitQuizAnswer(question: QuizQuestion) {
        val selected = _quizSelectedAnswers.value[question.id] ?: return
        val currentConfirmed = _quizAnswerConfirmed.value.toMutableMap()
        currentConfirmed[question.id] = true
        _quizAnswerConfirmed.value = currentConfirmed

        val isCorrect = selected == question.correctIndex
        val currentProfile = _userProfile.value
        if (isCorrect) {
            val updated = currentProfile.copy(
                correctQuizzesCount = currentProfile.correctQuizzesCount + 1,
                totalQuizzesTaken = currentProfile.totalQuizzesTaken + 1,
                coins = currentProfile.coins + 5,
                xp = currentProfile.xp + 10
            )
            updateProfile(updated)
        } else {
            val newHearts = if (currentProfile.isProSubscriber) currentProfile.hearts else (currentProfile.hearts - 1).coerceAtLeast(0)
            val updated = currentProfile.copy(
                totalQuizzesTaken = currentProfile.totalQuizzesTaken + 1,
                hearts = newHearts
            )
            updateProfile(updated)
        }
    }

    fun updateCodeText(newText: String) {
        _codeEditorText.value = newText
        _challengeFeedback.value = null
    }

    fun resetCodeChallenge() {
        val lesson = _activeLesson.value ?: return
        _codeEditorText.value = lesson.codingChallenge.startingCode
        _challengeFeedback.value = null
    }

    fun checkAndRunCode() {
        val lesson = _activeLesson.value ?: return
        val code = _codeEditorText.value
        val challenge = lesson.codingChallenge

        val missingKeywords = challenge.requiredKeywords.filter { !code.contains(it) }

        if (missingKeywords.isNotEmpty()) {
            _challengeFeedback.value = ChallengeFeedback(
                isSuccess = false,
                message = "Xatolik: Kod to'liq emas!",
                detail = "Quyidagi elementlar yetishmayapti: ${missingKeywords.joinToString(", ")}. Maslahat: ${challenge.hint}"
            )
        } else {
            _challengeFeedback.value = ChallengeFeedback(
                isSuccess = true,
                message = "Barakalla! Kod to'g'ri ishga tushdi! 🎉",
                detail = "Flutter vidjeti simulyatorda chizildi va vazifa bajarildi!"
            )
            val currentProfile = _userProfile.value
            val updatedChallenges = currentProfile.completedChallengeIds + challenge.id
            updateProfile(currentProfile.copy(completedChallengeIds = updatedChallenges))
        }
    }

    fun completeActiveLesson() {
        val lesson = _activeLesson.value ?: return
        val profile = _userProfile.value

        val isFirstTime = !profile.completedLessonIds.contains(lesson.id)
        val earnedXp = if (isFirstTime) lesson.xpReward else 15
        val earnedCoins = if (isFirstTime) lesson.coinReward else 5

        celebrationXp = earnedXp
        celebrationCoins = earnedCoins

        val updatedCompleted = profile.completedLessonIds + lesson.id
        // Auto unlock next lesson if applicable
        val nextLesson = allLessons.find { it.orderNumber == lesson.orderNumber + 1 }
        val updatedUnlocked = if (nextLesson != null) {
            profile.unlockedLessonIds + nextLesson.id
        } else {
            profile.unlockedLessonIds
        }

        val updatedProfile = profile.copy(
            xp = profile.xp + earnedXp,
            coins = profile.coins + earnedCoins,
            completedLessonIds = updatedCompleted,
            unlockedLessonIds = updatedUnlocked
        )

        updateProfile(updatedProfile)
        _showCelebration.value = true
    }

    fun closeCelebration() {
        _showCelebration.value = false
        _currentScreen.value = AppNavScreen.LESSONS
    }

    fun unlockLessonWithCoins(lesson: Lesson) {
        val profile = _userProfile.value
        if (profile.coins >= lesson.unlockCost) {
            val updated = profile.copy(
                coins = profile.coins - lesson.unlockCost,
                unlockedLessonIds = profile.unlockedLessonIds + lesson.id
            )
            updateProfile(updated)
            _unlockTargetLesson.value = null
            openLesson(lesson)
        }
    }

    fun triggerRewardAdForLesson(lesson: Lesson) {
        adTargetLessonId = lesson.id
        adPurpose = "'${lesson.title}' darsini ochish"
        _unlockTargetLesson.value = null
        _showAdDialog.value = true
    }

    fun triggerRewardAdForCoins() {
        adTargetLessonId = null
        adPurpose = "+50 Tanga va +25 XP mukofoti"
        _showStoreDialog.value = false
        _showAdDialog.value = true
    }

    fun onRewardEarned() {
        val profile = _userProfile.value
        val targetId = adTargetLessonId
        if (targetId != null) {
            val updated = profile.copy(
                unlockedLessonIds = profile.unlockedLessonIds + targetId,
                coins = profile.coins + 15
            )
            updateProfile(updated)
            val lesson = allLessons.find { it.id == targetId }
            if (lesson != null) {
                openLesson(lesson)
            }
        } else {
            val updated = profile.copy(
                coins = profile.coins + 50,
                xp = profile.xp + 25
            )
            updateProfile(updated)
        }
        _showAdDialog.value = false
        adTargetLessonId = null
    }

    fun toggleDownloadLesson(lessonId: String) {
        val profile = _userProfile.value
        val downloaded = profile.downloadedLessonIds.toMutableSet()
        if (downloaded.contains(lessonId)) {
            downloaded.remove(lessonId)
        } else {
            downloaded.add(lessonId)
        }
        updateProfile(profile.copy(downloadedLessonIds = downloaded))
    }

    fun activatePro() {
        val profile = _userProfile.value
        val allIds = allLessons.map { it.id }.toSet()
        val updated = profile.copy(
            isProSubscriber = true,
            hearts = 5,
            unlockedLessonIds = profile.unlockedLessonIds + allIds
        )
        updateProfile(updated)
        _showStoreDialog.value = false
    }

    fun claimDailyBonus() {
        val profile = _userProfile.value
        val updated = profile.copy(
            coins = profile.coins + 30,
            xp = profile.xp + 20,
            streakDays = profile.streakDays + 1,
            lastDailyClaimDate = System.currentTimeMillis()
        )
        updateProfile(updated)
    }

    fun refillHearts() {
        val profile = _userProfile.value
        if (profile.coins >= 25) {
            val updated = profile.copy(
                coins = profile.coins - 25,
                hearts = 5
            )
            updateProfile(updated)
        }
    }

    fun buyCoins(amount: Int) {
        val profile = _userProfile.value
        val updated = profile.copy(coins = profile.coins + amount)
        updateProfile(updated)
    }

    fun claimAchievement(achievementId: String) {
        val profile = _userProfile.value
        val achievements = repository.getAchievements(profile)
        val target = achievements.find { it.id == achievementId } ?: return

        if (target.isCompleted && !target.isClaimed) {
            val updatedClaimed = profile.claimedAchievementIds + achievementId
            val updated = profile.copy(
                claimedAchievementIds = updatedClaimed,
                coins = profile.coins + target.coinReward,
                xp = profile.xp + target.xpReward
            )
            updateProfile(updated)
        }
    }

    fun toggleDarkMode() {
        val profile = _userProfile.value
        val next = when (profile.isDarkMode) {
            true -> false
            false -> null
            null -> true
        }
        updateProfile(profile.copy(isDarkMode = next))
    }

    fun updateUsername(newName: String) {
        if (newName.isNotBlank()) {
            updateProfile(_userProfile.value.copy(username = newName.trim()))
        }
    }

    fun updateAvatar(emoji: String) {
        updateProfile(_userProfile.value.copy(avatarEmoji = emoji))
    }

    fun setFilterOnlyOffline(onlyOffline: Boolean) {
        _filterOnlyOffline.value = onlyOffline
    }

    fun setSelectedModuleFilter(moduleNumber: Int?) {
        _selectedModuleFilter.value = moduleNumber
    }

    fun openStore() {
        _showStoreDialog.value = true
    }

    fun closeStore() {
        _showStoreDialog.value = false
    }

    fun dismissUnlockDialog() {
        _unlockTargetLesson.value = null
    }

    fun dismissAdDialog() {
        _showAdDialog.value = false
        adTargetLessonId = null
    }

    fun getAchievements(): List<Achievement> {
        return repository.getAchievements(_userProfile.value)
    }

    fun getLeaderboard(): List<LeaderboardEntry> {
        return repository.getLeaderboard(_userProfile.value)
    }

    private fun updateProfile(newProfile: UserProfile) {
        _userProfile.value = newProfile
        repository.updateProfile(newProfile)
    }
}
