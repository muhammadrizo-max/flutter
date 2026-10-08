package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppNavScreen
import com.example.ui.MainViewModel
import com.example.ui.components.RewardedAdDialog
import com.example.ui.components.StoreDialog
import com.example.ui.components.TopStatsBar
import com.example.ui.components.UnlockLessonDialog
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.LessonStudyScreen
import com.example.ui.screens.RoadmapScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val profile by mainViewModel.userProfile.collectAsState()
            val systemDark = isSystemInDarkTheme()
            val isDarkTheme = profile.isDarkMode ?: systemDark

            MyApplicationTheme(darkTheme = isDarkTheme) {
                FlutterQuestApp(
                    viewModel = mainViewModel,
                    isDarkTheme = isDarkTheme
                )
            }
        }
    }
}

@Composable
fun FlutterQuestApp(
    viewModel: MainViewModel,
    isDarkTheme: Boolean
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val profile by viewModel.userProfile.collectAsState()
    val activeLesson by viewModel.activeLesson.collectAsState()
    val studyStep by viewModel.studyStep.collectAsState()
    val quizAnswers by viewModel.quizSelectedAnswers.collectAsState()
    val quizConfirmed by viewModel.quizAnswerConfirmed.collectAsState()
    val codeText by viewModel.codeEditorText.collectAsState()
    val challengeFeedback by viewModel.challengeFeedback.collectAsState()
    val filterOnlyOffline by viewModel.filterOnlyOffline.collectAsState()
    val selectedModuleFilter by viewModel.selectedModuleFilter.collectAsState()

    val showStore by viewModel.showStoreDialog.collectAsState()
    val showAd by viewModel.showAdDialog.collectAsState()
    val unlockTarget by viewModel.unlockTargetLesson.collectAsState()
    val showCelebration by viewModel.showCelebration.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentScreen != AppNavScreen.STUDY_LESSON) {
                TopStatsBar(
                    profile = profile,
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { viewModel.toggleDarkMode() },
                    onOpenStore = { viewModel.openStore() },
                    onProfileClick = { viewModel.navigateTo(AppNavScreen.PROFILE) }
                )
            }
        },
        bottomBar = {
            if (currentScreen != AppNavScreen.STUDY_LESSON) {
                NavigationBar(
                    modifier = Modifier.navigationBarsPadding(),
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 3.dp
                ) {
                    NavigationBarItem(
                        selected = currentScreen == AppNavScreen.LESSONS,
                        onClick = { viewModel.navigateTo(AppNavScreen.LESSONS) },
                        icon = { Icon(imageVector = Icons.Default.Map, contentDescription = "Xarita") },
                        label = { Text("Xarita") },
                        modifier = Modifier.testTag("nav_item_lessons")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AppNavScreen.LEADERBOARD,
                        onClick = { viewModel.navigateTo(AppNavScreen.LEADERBOARD) },
                        icon = { Icon(imageVector = Icons.Default.Leaderboard, contentDescription = "Reyting") },
                        label = { Text("Reyting") },
                        modifier = Modifier.testTag("nav_item_leaderboard")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AppNavScreen.PROFILE,
                        onClick = { viewModel.navigateTo(AppNavScreen.PROFILE) },
                        icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profil") },
                        label = { Text("Profil") },
                        modifier = Modifier.testTag("nav_item_profile")
                    )
                }
            }
        }
    ) { innerPadding ->
        when (currentScreen) {
            AppNavScreen.LESSONS -> {
                RoadmapScreen(
                    viewModel = viewModel,
                    profile = profile,
                    allLessons = viewModel.allLessons,
                    onSelectLesson = { viewModel.openLesson(it) },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            AppNavScreen.STUDY_LESSON -> {
                if (activeLesson != null) {
                    LessonStudyScreen(
                        viewModel = viewModel,
                        lesson = activeLesson!!,
                        profile = profile,
                        studyStep = studyStep,
                        quizSelectedAnswers = quizAnswers,
                        quizAnswerConfirmed = quizConfirmed,
                        codeEditorText = codeText,
                        challengeFeedback = challengeFeedback,
                        showCelebration = showCelebration,
                        onBack = { viewModel.navigateTo(AppNavScreen.LESSONS) },
                        modifier = Modifier.padding(innerPadding)
                    )
                } else {
                    viewModel.navigateTo(AppNavScreen.LESSONS)
                }
            }

            AppNavScreen.LEADERBOARD -> {
                LeaderboardScreen(
                    entries = viewModel.getLeaderboard(),
                    modifier = Modifier.padding(innerPadding)
                )
            }

            AppNavScreen.PROFILE -> {
                ProfileScreen(
                    viewModel = viewModel,
                    profile = profile,
                    allLessons = viewModel.allLessons,
                    achievements = viewModel.getAchievements(),
                    isDarkTheme = isDarkTheme,
                    onOpenLesson = { viewModel.openLesson(it) },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }

    // UNLOCK LESSON DIALOG
    if (unlockTarget != null) {
        UnlockLessonDialog(
            lesson = unlockTarget!!,
            profile = profile,
            onUnlockWithCoins = { viewModel.unlockLessonWithCoins(unlockTarget!!) },
            onUnlockWithAd = { viewModel.triggerRewardAdForLesson(unlockTarget!!) },
            onOpenProStore = {
                viewModel.dismissUnlockDialog()
                viewModel.openStore()
            },
            onDismiss = { viewModel.dismissUnlockDialog() }
        )
    }

    // REWARDED AD DIALOG
    if (showAd) {
        RewardedAdDialog(
            purposeTitle = viewModel.adPurpose,
            onRewardEarned = { viewModel.onRewardEarned() },
            onDismiss = { viewModel.dismissAdDialog() }
        )
    }

    // STORE / MONETIZATION MODAL
    if (showStore) {
        StoreDialog(
            profile = profile,
            onActivatePro = { viewModel.activatePro() },
            onWatchAdForCoins = { viewModel.triggerRewardAdForCoins() },
            onRefillHearts = { viewModel.refillHearts() },
            onClaimDailyBonus = { viewModel.claimDailyBonus() },
            onBuyCoins = { viewModel.buyCoins(it) },
            onDismiss = { viewModel.closeStore() }
        )
    }
}
