package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.model.Lesson
import com.example.model.QuizQuestion
import com.example.model.UserProfile
import com.example.ui.ChallengeFeedback
import com.example.ui.MainViewModel
import com.example.ui.components.FlutterPreviewCanvas

@Composable
fun LessonStudyScreen(
    viewModel: MainViewModel,
    lesson: Lesson,
    profile: UserProfile,
    studyStep: Int,
    quizSelectedAnswers: Map<String, Int>,
    quizAnswerConfirmed: Map<String, Boolean>,
    codeEditorText: String,
    challengeFeedback: ChallengeFeedback?,
    showCelebration: Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val isDownloaded = profile.downloadedLessonIds.contains(lesson.id)

    Scaffold(
        modifier = modifier.testTag("lesson_study_screen"),
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp,
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBack, modifier = Modifier.testTag("study_back_button")) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Orqaga")
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = lesson.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                maxLines = 1
                            )
                            Text(
                                text = lesson.moduleTitle,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // STEP TABS
                    TabRow(
                        selectedTabIndex = studyStep,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    ) {
                        Tab(
                            selected = studyStep == 0,
                            onClick = { viewModel.setStudyStep(0) },
                            text = { Text("1. Nazariya 📖", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                        )
                        Tab(
                            selected = studyStep == 1,
                            onClick = { viewModel.setStudyStep(1) },
                            text = { Text("2. Test 🧠", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                        )
                        Tab(
                            selected = studyStep == 2,
                            onClick = { viewModel.setStudyStep(2) },
                            text = { Text("3. Kodlash 💻", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (studyStep) {
                0 -> TheoryStepView(
                    lesson = lesson,
                    onNext = { viewModel.setStudyStep(1) }
                )

                1 -> QuizStepView(
                    lesson = lesson,
                    selectedAnswers = quizSelectedAnswers,
                    confirmedAnswers = quizAnswerConfirmed,
                    onSelectAnswer = { qId, optIndex -> viewModel.selectQuizOption(qId, optIndex) },
                    onSubmitAnswer = { question -> viewModel.submitQuizAnswer(question) },
                    onNext = { viewModel.setStudyStep(2) }
                )

                2 -> CodingStepView(
                    lesson = lesson,
                    codeText = codeEditorText,
                    feedback = challengeFeedback,
                    onCodeChange = { viewModel.updateCodeText(it) },
                    onRunCode = { viewModel.checkAndRunCode() },
                    onResetCode = { viewModel.resetCodeChallenge() },
                    onFinishLesson = { viewModel.completeActiveLesson() }
                )
            }

            // CELEBRATION MODAL
            if (showCelebration) {
                CelebrationDialog(
                    xpEarned = viewModel.celebrationXp,
                    coinsEarned = viewModel.celebrationCoins,
                    onContinue = { viewModel.closeCelebration() }
                )
            }
        }
    }
}

@Composable
fun TheoryStepView(
    lesson: Lesson,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        lesson.theorySections.forEach { section ->
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = section.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = section.content,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    )

                    if (section.codeSnippet != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF0F172A))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = section.codeSnippet,
                                color = Color(0xFF38BDF8),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }

                    if (section.flutterTip != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF59E0B).copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "💡", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = section.flutterTip,
                                    fontSize = 11.sp,
                                    color = Color(0xFFB45309),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("proceed_to_quiz_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
        ) {
            Text(text = "Test Savollariga O'tish ➡️", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun QuizStepView(
    lesson: Lesson,
    selectedAnswers: Map<String, Int>,
    confirmedAnswers: Map<String, Boolean>,
    onSelectAnswer: (String, Int) -> Unit,
    onSubmitAnswer: (QuizQuestion) -> Unit,
    onNext: () -> Unit
) {
    val allQuestionsConfirmed = lesson.quizQuestions.all { confirmedAnswers[it.id] == true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Dars bo'yicha kichik testlar (${lesson.quizQuestions.size} ta)",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        lesson.quizQuestions.forEachIndexed { index, question ->
            val selectedOption = selectedAnswers[question.id]
            val isConfirmed = confirmedAnswers[question.id] == true

            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "Savol #${index + 1}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        if (isConfirmed) {
                            val isCorrect = selectedOption == question.correctIndex
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isCorrect) Color(0xFF10B981) else Color(0xFFEF4444)
                            ) {
                                Text(
                                    text = if (isCorrect) "To'g'ri ✅ (+10 XP)" else "Xato ❌ (-1 ❤️)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = question.question,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // OPTIONS
                    question.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedOption == optIndex
                        val isCorrectOption = optIndex == question.correctIndex

                        val backgroundColor = when {
                            !isConfirmed && isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            isConfirmed && isCorrectOption -> Color(0xFF10B981).copy(alpha = 0.2f)
                            isConfirmed && isSelected && !isCorrectOption -> Color(0xFFEF4444).copy(alpha = 0.2f)
                            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        }

                        val borderColor = when {
                            !isConfirmed && isSelected -> MaterialTheme.colorScheme.primary
                            isConfirmed && isCorrectOption -> Color(0xFF10B981)
                            isConfirmed && isSelected && !isCorrectOption -> Color(0xFFEF4444)
                            else -> Color.Transparent
                        }

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable(enabled = !isConfirmed) {
                                    onSelectAnswer(question.id, optIndex)
                                },
                            shape = RoundedCornerShape(12.dp),
                            color = backgroundColor,
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFCBD5E1)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${('A'.code + optIndex).toChar()}",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = optionText,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    // CHECK ANSWER BUTTON
                    if (!isConfirmed) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { onSubmitAnswer(question) },
                            enabled = selectedOption != null,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                        ) {
                            Text("Javobni Tekshirish")
                        }
                    } else {
                        // EXPLANATION
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = "Tushuntirish: ${question.explanation}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("proceed_to_coding_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
        ) {
            Text(
                text = if (allQuestionsConfirmed) "Kodlash Mashqiga O'tish 💻 ➡️" else "Kodlashga O'tish ➡️",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun CodingStepView(
    lesson: Lesson,
    codeText: String,
    feedback: ChallengeFeedback?,
    onCodeChange: (String) -> Unit,
    onRunCode: () -> Unit,
    onResetCode: () -> Unit,
    onFinishLesson: () -> Unit
) {
    val challenge = lesson.codingChallenge

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // INSTRUCTION CARD
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Vazifa: ${challenge.title}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    TextButton(onClick = onResetCode) {
                        Text("Qayta o'rnatish ↺", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = challenge.instruction,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF38BDF8).copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "Maslahat: ${challenge.hint}",
                        fontSize = 11.sp,
                        color = Color(0xFF0369A1),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // CODE EDITOR
        Text(
            text = "Dart Kod Muharriri (main.dart):",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        OutlinedTextField(
            value = codeText,
            onValueChange = onCodeChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF0F172A))
                .testTag("code_editor_input"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFFF8FAFC),
                unfocusedTextColor = Color(0xFFF8FAFC),
                focusedBorderColor = Color(0xFF38BDF8),
                unfocusedBorderColor = Color(0xFF334155),
                cursorColor = Color(0xFF38BDF8)
            ),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 13.sp,
                lineHeight = 18.sp
            ),
            keyboardOptions = KeyboardOptions.Default
        )

        // RUN CODE ACTION
        Button(
            onClick = onRunCode,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("run_code_button"),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Kodni Ishga Tushirish (Run & Hot Reload)", fontWeight = FontWeight.Bold)
        }

        // FEEDBACK BANNER
        if (feedback != null) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (feedback.isSuccess) Color(0xFF10B981).copy(alpha = 0.15f) else Color(0xFFEF4444).copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (feedback.isSuccess) Color(0xFF10B981) else Color(0xFFEF4444)
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = feedback.message,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = if (feedback.isSuccess) Color(0xFF059669) else Color(0xFFDC2626)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = feedback.detail,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // SIMULATED FLUTTER RUNTIME CANVAS
        Text(
            text = "Flutter UI Chiqishi (Simulyator):",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        FlutterPreviewCanvas(
            widgetType = challenge.widgetType,
            codeText = codeText
        )

        Spacer(modifier = Modifier.height(10.dp))

        // FINISH LESSON BUTTON
        Button(
            onClick = onFinishLesson,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("finish_lesson_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (feedback?.isSuccess == true) Color(0xFF10B981) else Color(0xFF0284C7)
            )
        ) {
            Text(
                text = "Darsni Muvaffaqiyatli Yakunlash 🏆",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun CelebrationDialog(
    xpEarned: Int,
    coinsEarned: Int,
    onContinue: () -> Unit
) {
    Dialog(onDismissRequest = onContinue) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("celebration_dialog"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Gold Trophy Badge
                Image(
                    painter = painterResource(id = R.drawable.flutter_badge_gold_1791449985949),
                    contentDescription = "Oltin mukofot",
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Ajoyib Natija! 🎉",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Siz darsni muvaffaqiyatli yakunladingiz va mukofotlarga ega bo'ldingiz!",
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF0284C7).copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "+$xpEarned XP ⭐",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF0284C7),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFFB300).copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "+$coinsEarned Tanga 🪙",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFFD97706),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("celebration_continue_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                ) {
                    Text("Davom Etish 🚀", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}
